package compiler;

import java.util.*;
import java.util.stream.Collectors;

import compiler.AST.*;
import compiler.exc.*;
import compiler.lib.*;

/**
 * Visits and enriches an AST producing an EAST.
 * Enrichment is performed top-down.
 * <p>
 * Processes for each scope:
 * > `the declarations` adding new entries
 * to the symbol table and reports any variable/methods
 * that is multiply declared.
 * > `the statements` finding uses of undeclared variables and adding a pointer
 * to the appropriate symbol table entry in ID nodes.
 * <p>
 * ASSUMPTIONS:
 * -> Out language uses STATIC SCOPING
 * -> All names must be declared BEFORE they are used
 * -> Multiple declaration of a name `in the same scope` is NOT ALLOWED
 * -> Multiple declaration is ALLOWED in multiple NESTED scopes
 */
public class SymbolTableASTVisitor extends BaseASTVisitor<Void, VoidException> {

    private final List<Map<String, STentry>> symTable = new ArrayList<>();
    /* Mappa ogni nome di classe nella propria Virtual Table:
	serve per preservare le dichiarazioni interne ad una classe (campi e metodi) una volta che il visitor ha
	concluso la dichiarazione di una classe. Le rende accessibili anche in seguito tramite il nome della classe.
	Nota: Virtual Table è il nome che si da alla Symbol Table all'interno delle classi. */
    Map<String, Map<String, STentry>> classTable = new HashMap<>(); //-- Slide 20/51.
    private int nestingLevel = 0; // current nesting level
    private int declarationOffset = -2; // counter for offset of local declarations at current nesting level
    int stErrors = 0;
    Set<String> onClassVisitScope; // variabile per il conteggio degli scope, durante la visita di una classe.

    SymbolTableASTVisitor() {
    }

    // enables print for debugging
    SymbolTableASTVisitor(boolean debug) {
        super(debug);
    }

    private STentry stLookup(String id) {
        int tableIndex = nestingLevel;
        STentry entry = null;
        while (tableIndex >= 0 && entry == null) {
            entry = symTable.get(tableIndex--).get(id);
        }
        return entry;
    }

    @Override
    public Void visitNode(ProgLetInNode node) {
        if (print) printNode(node);

        Map<String, STentry> globalScopeTable = new HashMap<>();
        symTable.add(globalScopeTable);
        for (Node declaration : node.declarationList) {
            visit(declaration);
        }
        visit(node.expression);
        symTable.remove(0);
        return null;
    }

    @Override
    public Void visitNode(ProgNode node) {
        if (print) printNode(node);
        visit(node.expression);
        return null;
    }

    /* FunNode section */
    @Override
    public Void visitNode(FunNode node) {
        if (print) printNode(node);

        TypeNode functionType = buildFunctionType(node);

        if (!insertIdIntoSymbolTable(node, functionType)) {
            System.out.println("Fun id " + node.id + " at line " + node.getLine() + " already declared");
            stErrors++;
        }

        Map<String, STentry> functionScopeTable = createNewFunctionScope();
        int previousNestingLevelDeclarationOffset = setDeclarationOffsetForFunction();

        addParametersToSymbolTable(node, functionScopeTable);
        visitDeclarationsInFunction(node);
        visit(node.expression);
        symTable.remove(nestingLevel--);
        declarationOffset = previousNestingLevelDeclarationOffset;
        return null;
    }

    private TypeNode buildFunctionType(FunNode node) {
        List<TypeNode> parametersTypes = new ArrayList<>();
        for (ParNode parameter : node.parametersList) {
            parametersTypes.add(parameter.getType());
        }
        return new ArrowTypeNode(parametersTypes, node.returnType);
    }

    private boolean insertIdIntoSymbolTable(FunNode node, TypeNode functionType) {
        final Map<String, STentry> scopeTable = symTable.get(nestingLevel);
        final STentry entry = new STentry(nestingLevel, functionType, declarationOffset--);
        return scopeTable.put(node.id, entry) == null;
    }

    private Map<String, STentry> createNewFunctionScope() {
        nestingLevel++;
        Map<String, STentry> functionScopeTable = new HashMap<>();
        symTable.add(functionScopeTable);
        return functionScopeTable;
    }

    private int setDeclarationOffsetForFunction() {
        int previousNestingLevelDeclarationOffset = declarationOffset;
        declarationOffset = -2;
        return previousNestingLevelDeclarationOffset;
    }

    private void addParametersToSymbolTable(FunNode node, Map<String, STentry> functionScopeTable) {
        int parameterOffset = 1;
        for (ParNode parameter : node.parametersList) {
            final STentry parameterEntry = new STentry(nestingLevel, parameter.getType(), parameterOffset++);
            if (functionScopeTable.put(parameter.id, parameterEntry) != null) {
                System.out.println("Par id " + parameter.id + " at line " + node.getLine() + " already declared");
                stErrors++;
            }
        }
    }

    private void visitDeclarationsInFunction(FunNode node) {
        for (Node declaration : node.declarationsList) {
            visit(declaration);
        }
    }

    /* End FunNode section */

    @Override
    public Void visitNode(VarNode node) {
        if (print) printNode(node);
        Map<String, STentry> currentScopeTable = symTable.get(nestingLevel);

        //Insert the new entry in the symbol table
        STentry entry = new STentry(nestingLevel, node.getType(), declarationOffset--);
        if (currentScopeTable.put(node.id, entry) != null) {
            System.out.println("Var id " + node.id + " at line " + node.getLine() + " already declared");
            stErrors++;
        }
        visit(node.expression);
        return null;
    }

    @Override
    public Void visitNode(PrintNode node) {
        if (print) printNode(node);
        visit(node.expression);
        return null;
    }

    @Override
    public Void visitNode(IfNode node) {
        if (print) printNode(node);
        visit(node.condition);
        visit(node.thenBranch);
        visit(node.elseBranch);
        return null;
    }

    @Override
    public Void visitNode(EqualNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(TimesNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(PlusNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(CallNode node) {
        if (print) printNode(node);
        STentry entry = stLookup(node.id);
        if (entry == null) {
            System.out.println("Fun or Method with id " + node.id + " at line " + node.getLine() + " not declared");
            stErrors++;
        }
        else {
            node.callNodeSTEntry = entry;
            node.nestingLevel = nestingLevel;
        }
        for (Node argument : node.argumentsList) {
            visit(argument);
        }
        return null;
    }

    @Override
    public Void visitNode(IdNode node) {
        if (print) printNode(node);
        STentry entry = stLookup(node.id);
        if (entry == null) {
            System.out.println("Var or Par id " + node.id + " at line " + node.getLine() + " not declared");
            stErrors++;
        }
        else {
            node.symbolTableEntry = entry;
            node.nestingLevel = nestingLevel;
        }
        return null;
    }

    @Override
    public Void visitNode(BoolNode node) {
        if (print) printNode(node, node.value.toString());
        return null;
    }

    @Override
    public Void visitNode(IntNode node) {
        if (print) printNode(node, node.value.toString());
        return null;
    }

    /* ************** */
    /* Operators PART */
    /* ************** */

    @Override
    public Void visitNode(LessEqualNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(GreaterEqualNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(OrNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(AndNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(DivNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(MinusNode node) {
        if (print) printNode(node);
        visit(node.left);
        visit(node.right);
        return null;
    }

    @Override
    public Void visitNode(NotNode node) {
        if (print)  printNode(node);
        visit(node.expression);
        return null;
    }

    /* ********* */
    /*  OOP PART */
    /* ********* */

    public Void visitNode(ClassNode node) {
        if (print) printNode(node);

        var classType = new ClassTypeNode(new ArrayList<>(), new ArrayList<>());
        onClassVisitScope = new HashSet<String>(); //Ottimizzazione-1  pagina 47/51.

        /* Inizio - Fase di controllo ereditarietà. */

        if (node.superID != null && classTable.containsKey(node.superID)) {
            STentry superClassEntry = symTable.get(0).get(node.superID);
            classType = new ClassTypeNode(
                    new ArrayList<>(((ClassTypeNode) superClassEntry.type).allFields),
                    new ArrayList<>(((ClassTypeNode) superClassEntry.type).allMethods)
            );
            node.superClassSTEntry = superClassEntry;
        } else if (node.superID != null) {
            System.out.println("Extending class id " + node.superID + " at line " + node.getLine() + " is not declared");
        }
        STentry entry = new STentry(0, classType, declarationOffset--);
        node.classType = classType;
        /* Fine - Fase di controllo ereditarietà. */

        // Check if the class is already declared.
        Map<String, STentry> globalScopeTable = symTable.get(0);
        if (globalScopeTable.put(node.id, entry) != null) {
            System.out.println("Class id " + node.id + " at line " + node.getLine() + " already declared");
            stErrors++;
        }

        // Add the scope of the class to the symbol table.
        nestingLevel++;
        Map<String, STentry> virtualTable = new HashMap<>();
        // Add the methods of the superclass (if extends) to the virtual table.
        var superClassVirtualTable = classTable.get(node.superID);
        if (node.superID != null) {
            virtualTable.putAll(superClassVirtualTable);
        }
        classTable.put(node.id, virtualTable);
        symTable.add(virtualTable);

        // Setting the offset for the fields of the class. -- Slide 26/51.
        int fieldOffset = -1;
        if (node.superID != null) {
            fieldOffset = -((ClassTypeNode) symTable.get(0).get(node.superID).type).allFields.size()-1;
        }

        // Field section: add all fields to the virtual table
        for (var field : node.fields) {
            if (onClassVisitScope.contains(field.id)) {
                System.out.println("Field with id " + field.id + " on line " + field.getLine() + " was already declared");
                stErrors++;
            }
            onClassVisitScope.add(field.id);
            var overriddenFieldEntry = virtualTable.get(field.id);
            STentry fieldEntry;
            if (overriddenFieldEntry != null && !(overriddenFieldEntry.type instanceof MethodTypeNode)) {
                fieldEntry = new STentry(nestingLevel, field.getType(), overriddenFieldEntry.offset);
                classType.allFields.set(-fieldEntry.offset - 1, fieldEntry.type);
            } else {
                fieldEntry = new STentry(nestingLevel, field.getType(), fieldOffset--);
                classType.allFields.add(-fieldEntry.offset - 1, fieldEntry.type);
                if (overriddenFieldEntry != null) {
                    System.out.println("Cannot override field id " + field.id + " with a method");
                    stErrors++;
                }
            }
            virtualTable.put(field.id, fieldEntry);
            field.offset = fieldEntry.offset;
        }

        // Method section: add all methods to the virtual table
        int currentDecOffset = declarationOffset;
        int previousNestingLevelDeclarationOffset = declarationOffset;
        declarationOffset = 0;
        if (node.superID != null) {
            declarationOffset = ((ClassTypeNode) symTable.get(0).get(node.superID).type).allMethods.size();
        }
        for (var method : node.methods) {
            if (onClassVisitScope.contains(method.id)) {
                System.out.println("Method with id " + method.id + " on line " + method.getLine() + " was already declared");
                stErrors++;
            }
            visit(method);
            classType.allMethods.add(method.offset, ((MethodTypeNode) virtualTable.get(method.id).type).functionalType);
        }

        // Restore the previous declaration offset.
        declarationOffset = currentDecOffset;
        symTable.remove(nestingLevel--);
        declarationOffset = previousNestingLevelDeclarationOffset;
        return null;
    }

    @Override
    public Void visitNode(MethodNode node) {
        if (print) printNode(node);

        Map<String, STentry> currentScopeTable = symTable.get(nestingLevel);
        List<TypeNode> paramTypes = new ArrayList<>();
        for (ParNode parameter : node.parametersList) {
            paramTypes.add(parameter.getType());
        }

        // Inserting the method in the Symbol Table. -- Slide 24/51.
        STentry overriddenMethodEntry = currentScopeTable.get(node.id);
        final TypeNode methodType = new MethodTypeNode(new ArrowTypeNode(paramTypes, node.retType));
        STentry entry = null;
        if (overriddenMethodEntry != null && overriddenMethodEntry.type instanceof MethodTypeNode) {
            entry = new STentry(nestingLevel, methodType, overriddenMethodEntry.offset);
        } else {
            entry = new STentry(nestingLevel, methodType, declarationOffset++);
            if (overriddenMethodEntry != null) {
                System.out.println("Cannot override method id " + node.id + " with a field");
                stErrors++;
            }
        }
        node.offset = entry.offset;
        currentScopeTable.put(node.id, entry);


        //Create a new scope for the method's parameters and declarations
        nestingLevel++;
        Map<String, STentry> methodScopeTable = new HashMap<>();
        symTable.add(methodScopeTable);
        int previousNestingLeveleDeclarationOffset = declarationOffset;
        declarationOffset = -2;
        int parameterOffset = 1;

        // Define the method's parameters in the symbol table
        for (ParNode parameter : node.parametersList) {
            final STentry parameterEntry = new STentry(nestingLevel, parameter.getType(), parameterOffset++);
            if (methodScopeTable.put(parameter.id, parameterEntry) != null) {
                System.out.println("Error: Parameter " + parameter.id + " already declared at line " + node.getLine());
                stErrors++;
            }
        }

        // Define the method's declarations in the symbol table
        for (Node declaration : node.declarationsList) {
            visit(declaration);
        }

        // Visit the method's expression
        visit(node.expression);

        // Exit the method scope
        symTable.remove(nestingLevel--);
        declarationOffset = previousNestingLeveleDeclarationOffset;
        return null;
    }

    @Override
    public Void visitNode(NewNode node) {
        if (print) printNode(node);
        // Check if the class id was already declared.
        if (!classTable.containsKey(node.id)) {
            System.out.println("Class id " + node.id + " was not declared");
            stErrors++;
        }
        // Istanziate the class.
        node.newClassSTEntry = symTable.get(0).get(node.id);
        for (var argument : node.argumentsList) {
            visit(argument);
        }
        return null;
    }

    @Override
    public Void visitNode(RefTypeNode node) {
        if (print) printNode(node, node.id);

        // Check if the class id was already declared.
        if (!classTable.containsKey(node.id)) {
            System.out.println("Class with id " + node.id + " on line " + node.getLine() + " was not declared");
            stErrors++;
        }
        return null;
    }

    @Override
    public Void visitNode(ClassCallNode node) {
        if (print) printNode(node);

        // Check if the object id was declared into the symbol table, with the use of stLookup.
        STentry entry = stLookup(node.objectId);
        if (entry == null) {
            System.out.println("Object id " + node.objectId + " at line " + node.getLine() + " not declared");
            stErrors++;
        }
        // Check if the object id is a class.
        else if (entry.type instanceof RefTypeNode)
        {
            // Check if the class has the method.
            node.classCallSTEntry = entry;
            node.nestingLevel = nestingLevel;
            node.classCallmethodSTEntry = classTable.get(((RefTypeNode) entry.type).id).get(node.methodId);
            if (node.classCallmethodSTEntry == null) {
                System.out.println("Object id " + node.objectId + " at line " + node.getLine() + " has no method " + node.methodId);
                stErrors++;
            }
        }
        for (Node argument : node.argumentsList) {
            visit(argument);
        }
        return null;
    }

    @Override
    public Void visitNode(EmptyNode node) {
        if (print) printNode(node);
        return null;
    }
}
