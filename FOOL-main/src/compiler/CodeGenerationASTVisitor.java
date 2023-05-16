package compiler;

import compiler.AST.*;
import compiler.lib.*;
import compiler.exc.*;
import svm.ExecuteVM;

import java.util.ArrayList;
import java.util.List;

import static compiler.lib.FOOLlib.*;
import static compiler.lib.FOOLlib.nlJoin;

public class CodeGenerationASTVisitor extends BaseASTVisitor<String, VoidException> {

    List<List<String>> dispatchTables = new ArrayList<>();

    CodeGenerationASTVisitor() {
    }

    CodeGenerationASTVisitor(boolean debug) {
        super(false, debug);
    }

    @Override
    public String visitNode(ProgLetInNode node) {
        if (print) printNode(node);
        String declarationListCode = null;
        for (Node declaration : node.declarationList) {
            declarationListCode = nlJoin(declarationListCode, visit(declaration));
        }
        return nlJoin(
            "push 0",
            declarationListCode, // generate code for declarations (allocation)
            visit(node.expression),
            "halt",
            getCode()
        );
    }

    @Override
    public String visitNode(ProgNode node) {
        if (print) printNode(node);
        return nlJoin(
            visit(node.expression),
            "halt"
        );
    }

    @Override
    public String visitNode(VarNode node) {
        if (print) printNode(node, node.id);
        return visit(node.expression);
    }

    @Override
    public String visitNode(FunNode node) {
        if (print) printNode(node, node.id);
        String declarationListCode = null;
        String popDeclarationsList = null;
        String popParametersList = null;
        for (Node declaration : node.declarationsList) {
            declarationListCode = nlJoin(declarationListCode, visit(declaration));
            popDeclarationsList = nlJoin(popDeclarationsList, "pop");
        }
        for (int i = 0; i < node.parametersList.size(); i++) {
            popParametersList = nlJoin(popParametersList, "pop");
        }
        String functionLabel = freshFunLabel();
        putCode(
            nlJoin(
                functionLabel + ":",
                "cfp", // set $fp to $sp value
                "lra", // load $ra value
                declarationListCode, // generate code for local declarations (they use the new $fp!!!)
                visit(node.expression), // generate code for function body expression
                "stm", // set $tm to popped value (function result)
                popDeclarationsList, // remove local declarations from stack
                "sra", // set $ra to popped value
                "pop", // remove Access Link from stack
                popParametersList, // remove parameters from stack
                "sfp", // set $fp to popped value (Control Link)
                "ltm", // load $tm value (function result)
                "lra", // load $ra value
                "js"  // jump to popped address
            )
        );
        return "push " + functionLabel;
    }

    @Override
    public String visitNode(IdNode node) {
        if (print) printNode(node, node.id);
        String getActivationRecordCode = null;
        for (int i = 0; i < node.nestingLevel - node.symbolTableEntry.nl; i++) {
            getActivationRecordCode = nlJoin(getActivationRecordCode, "lw");
        }
        return nlJoin(
            "lfp",
            /*
             * Retrieve address of frame containing "id" declaration by following the static chain (of Access Links)
             */
            getActivationRecordCode,
            "push " + node.symbolTableEntry.offset,
            "add", // compute address of "id" declaration
            "lw" // load value of "id" variable
        );
    }

    @Override
    public String visitNode(CallNode node) {
        if (print) printNode(node, node.id);
        String argumentsCode = null;
        String getActivationRecordCode = null;
        for (int i = node.argumentsList.size() - 1; i >= 0; i--) {
            argumentsCode = nlJoin(argumentsCode, visit(node.argumentsList.get(i)));
        }
        for (int i = 0; i < node.nestingLevel - node.callNodeSTEntry.nl; i++) {
            getActivationRecordCode = nlJoin(getActivationRecordCode, "lw");
        }
        String commonCode = nlJoin(
                "lfp", // load Control Link (pointer to frame of function "id" caller)
                argumentsCode, // generate code for argument expressions in reversed order
                "lfp", getActivationRecordCode, // retrieve address of frame containing "id" declaration
                // by following the static chain (of Access Links)
                "stm", // set $tm to popped value (with the aim of duplicating top of stack)
                "ltm", // load Access Link (pointer to frame of function "id" declaration)
                "ltm" // duplicate top of stack
        );
        if (node.callNodeSTEntry.type instanceof MethodTypeNode) {
            commonCode = nlJoin(commonCode, "lw"); // load dispatchPointer
        }
        return nlJoin(commonCode,
                "push " + node.callNodeSTEntry.offset,
                "add", // compute address of "id" declaration
                "lw", // load address of "id" function
                "js"  // jump to popped address (saving address of subsequent instruction in $ra)
        );
    }

    @Override
    public String visitNode(PrintNode node) {
        if (print) printNode(node);
        return nlJoin(
            visit(node.expression),
            "print"
        );
    }

    @Override
    public String visitNode(IfNode node) {
        if (print) printNode(node);
        String label1 = freshLabel();
        String label2 = freshLabel();
        return nlJoin(
            visit(node.condition),
            "push 1",
            "beq " + label1,
            visit(node.elseBranch),
            "b " + label2,
            label1 + ":",
            visit(node.thenBranch),
            label2 + ":"
        );
    }

    @Override
    public String visitNode(EqualNode node) {
        if (print) printNode(node);
        String label1 = freshLabel();
        String label2 = freshLabel();
        return nlJoin(
            visit(node.left),
            visit(node.right),
            "beq " + label1,
            "push 0",
            "b " + label2,
            label1 + ":",
            "push 1",
            label2 + ":"
        );
    }

    @Override
    public String visitNode(TimesNode node) {
        if (print) printNode(node);
        return nlJoin(
            visit(node.left),
            visit(node.right),
            "mult"
        );
    }

    @Override
    public String visitNode(PlusNode node) {
        if (print) printNode(node);
        return nlJoin(
            visit(node.left),
            visit(node.right),
            "add"
        );
    }

    @Override
    public String visitNode(BoolNode node) {
        if (print) printNode(node, node.value.toString());
        return "push " + (node.value ? 1 : 0);
    }

    @Override
    public String visitNode(IntNode node) {
        if (print) printNode(node, node.value.toString());
        return "push " + node.value;
    }


    /* ****************** */
    /* Operations SECTION */
    /* ****************** */

    @Override
    public String visitNode(MinusNode node) {
        if (print) printNode(node);
        return nlJoin(
                visit(node.left),
                visit(node.right),
                "sub"
        );
    }

    @Override
    public String visitNode(DivNode node) {
        if (print) printNode(node);
        return nlJoin(
                visit(node.left),
                visit(node.right),
                "div"
        );
    }

    @Override
    public String visitNode(GreaterEqualNode node) {
        if (print) printNode(node);
        String label1 = freshLabel();
        String label2 = freshLabel();
        return nlJoin(
                visit(node.right),
                visit(node.left),
                "sub",
                "push 0",
                "bleq " + label1,
                "push 0",
                "b " + label2,
                label1 + ":",
                "push 1",
                label2 + ":"
        );
    }

    @Override
    public String visitNode(LessEqualNode node) {
        if (print) printNode(node);
        String label1 = freshLabel();
        String label2 = freshLabel();
        return nlJoin(
                visit(node.left),
                visit(node.right),
                "bleq " + label1,
                "push 0",
                "b " + label2,
                label1 + ":",
                "push 1",
                label2 + ":"
        );
    }

    @Override
    public String visitNode(OrNode node) {
        if (print) printNode(node);
        String label1 = freshLabel();
        String label2 = freshLabel();
        String label3 = freshLabel();
        String label4 = freshLabel();
        return nlJoin(
                visit(node.left),
                "push 0",
                "beq " + label1,
                "b " + label2,
                label1 + ":",
                visit(node.right),
                "push 0",
                "beq " + label3,
                label2 + ":",
                "push 1",
                "b " + label4,
                label3 + ":",
                "push 0",
                label4 + ":"
        );
    }

    /* Nell'implementazione dell'&& utilizzo a mio favore la sua tabella di verità e noto che soltanto quando entrambi gli operatori che in questo caso
	 sono entrambi "boolean" assumono il valore 1 (True) l'and drovrà andare a buon fine, come fare a implementarlo? Semplicemente quando gli operatori
	visito il fattore sinistro e il fattore destro  e controllo che siano uguali a 1. Se entrambi lo sono allora l'and va buon fine
	e come per gli operatori di "greaterequal" o "minusequal" o "equal" instrada la soluzione con dei label, cioè se sono entrambi 1 pusha 1 nello stack,
	altrimenti 0. Inoltre, siccome disponiamo di uno stack a supporto, le operazioni assembly vengono valutate a 2 a 2. In uno stato q0 iniziale, il mio stack ha
    poi pusha un 1, controlla con BEQ se 1 e right sono uguali e se lo sono pusha 1 poi controlla che "left" sia uguale al risultato della precedente valutazione
    (che dovrà essere 1) e se lo è pusha 1. (Perciò && è true), in tutti gli altri casi pusha 0.
	 */

    @Override
    public String visitNode(AndNode node) {
        if (print) printNode(node);
        String label1 = freshLabel();
        String label2 = freshLabel();
        return nlJoin(
                visit(node.left),
                "push 0",
                "beq " + label1,
                visit(node.right),
                "push 0",
                "beq " + label1,
                "push 1",
                "b  " + label2,
                label1 + ":",
                "push 0",
                label2 + ":"
        );
    }

    @Override
    public String visitNode(NotNode node) {
        if (print) printNode(node);
        String label1 = freshLabel();
        String label2 = freshLabel();
        return nlJoin(
                visit(node.expression),
                "push 0",
                "beq " + label1,
                "push 0",
                "b " + label2,
                label1 + ":",
                "push 1",
                label2 + ":"
        );
    }

    /* ************ */
    /* OOPS SECTION */
    /* ************ */

    @Override
    public String visitNode(ClassNode node) {
        if (print) printNode(node, node.id);
        List<String> dispatchTable = new ArrayList<>();
        dispatchTables.add(dispatchTable);
        if (node.superID != null) {
            var superClassDispatchTable = dispatchTables.get(-node.superClassSTEntry.offset-2);
            dispatchTable.addAll(superClassDispatchTable);
        }
        for (int i = 0; i < node.methods.size(); i++) {
            var method = node.methods.get(i);
            visit(method);
            if (method.offset < dispatchTable.size()) {
                dispatchTable.set(method.offset, method.label);
            } else {
                dispatchTable.add(method.offset, method.label);
            }
        }
        String generationDispatchTable = null;
        for (String label : dispatchTable) {
            generationDispatchTable = nlJoin(
                    generationDispatchTable, // creo sullo heap la Dispatch Table che ho costruito, e la scorro... (ricorsivamente)
                    "push " + label, //per ciascuna etichetta...
                    "lhp",  //..metto valore di $hp sullo stack: sarà il dispatchpointer da ritornare alla fine
                    "sw", // carico la word sullo stack
                    "lhp", //  la memorizzo a indirizzo in $hp
                    "push 1",  //pusho 1 per incrementare la posizione
                    "add",  //sommo hp e 1 per aggiornare la posizione
                    "shp" // setto il valore incrementato $hp
            );
        }
        return nlJoin(
                "lhp", //alloca su heap la dispatch table della classe
                generationDispatchTable // e lascia il dispatch pointer sullo stack
        );
    }

    @Override
    public String visitNode(MethodNode node) {
        if (print) printNode(node, node.id);
        String declarationListCode = null;
        String popDeclarationsList = null;
        for (Node declaration : node.declarationsList) {
            declarationListCode = nlJoin(declarationListCode, visit(declaration));
            popDeclarationsList = nlJoin(popDeclarationsList, "pop");
        }
        String popParametersList = null;
        for (int i = 0; i < node.parametersList.size(); i++) {
            popParametersList = nlJoin(popParametersList, "pop");
        }
        String functionLabel = freshFunLabel();
        node.label = functionLabel;
        putCode(
                nlJoin(
                        functionLabel + ":",
                        "cfp", // setto $fp a $sp
                        "lra", // carico $ra sullo stack
                        declarationListCode, // genero codice per le dichiarazioni locali (variabili) usando il nuovo $fp!
                        visit(node.expression), // genero il codice per l'espressione
                        "stm", // salvo il risultato in $tm
                        popDeclarationsList, // rimuovo le dichiarazioni locali dallo stack
                        "sra", // setto $ra a $tm (risultato)
                        "pop", // rimuovo l'Access Link dallo stack
                        popParametersList, // rimuovo i parametri dallo stack
                        "sfp", // setto $fp al valore poppato che sarà Control Link
                        "ltm", // carico $tm che è il risultato della funzione
                        "lra", // carico $ra
                        "js"  // salto a $ra che è il mio return address.
                )
        );
        return null;
    }
    @Override
    public String visitNode(NewNode node) {
        if (print) printNode(node, node.id);
        String loadArgsOnStack = null;
        for(var argument : node.argumentsList) {
            loadArgsOnStack = nlJoin(
                    loadArgsOnStack,
                    visit(argument)
            );
        }
        String loadArgumentsOnHeap = null;
        for (var i = 0; i < node.argumentsList.size(); i++) {
            loadArgumentsOnHeap = nlJoin(
                    loadArgumentsOnHeap,
                    "lhp", //carico sullo stack il valore di $hp (indirizzo object pointer da ritornare) e incremento $hp...
                    "sw", // carico la parola sullo stack
                    "lhp", // la memorizzo a indirizzo in $hp
                    "push 1", // pusho 1 per incrementare la posizione
                    "add", // sommo hp e 1 per aggiornare la posizione
                    "shp"  // e setto il valore incrementato $hp.
            );
        }
        return nlJoin(
                loadArgsOnStack, //prendo il codice di loadArgsOnstack.
                loadArgumentsOnHeap, //prendo il codice di loadArgsOnHeap.
                "push " + ExecuteVM.MEMSIZE, //pusho lo spazio di memoria.
                "push " + node.newClassSTEntry.offset, //pusho l'offset.
                "add", //li sommo per ottenere il dispatch pointer.
                "lw", // prendo il dispatch pointer
                "lhp", // carica sullo stack il valore di $hp (indirizzo object pointer da ritornare) e incrementa $hp...
                "sw", //... da qui è come quello visto subito sopra per il ClassNode.
                "lhp",
                "lhp",
                "push 1",
                "add",
                "shp"
        );

        /*
		nota: anche se la classe ID non ha campi l’oggetto
			allocato contiene comunque il dispatch pointer
		 	== tra object pointer ottenuti da due new è sempre falso
	 	*/
    }

    @Override
    public String visitNode(ClassCallNode node) {
        if (print) printNode(node, node.objectId+"."+node.methodId);
        String argumentsCode = null;
        for (int i = node.argumentsList.size() - 1; i >= 0; i--) {
            argumentsCode = nlJoin(argumentsCode, visit(node.argumentsList.get(i)));
        }
        String getActivationRecordCode = null;
        for (int i = 0; i < node.nestingLevel - node.classCallSTEntry.nl; i++) {
            getActivationRecordCode = nlJoin(getActivationRecordCode, "lw");
        }
        return nlJoin(
                "lfp", // carico il Control Link (puntatore alla funzione "id" chiamata)
                argumentsCode, // genero il codice in maniera reverse per caricare gli argomenti sullo stack.
                "lfp", getActivationRecordCode, //cerco l'indirizzo del frame che contiene la dichiarazione di "id"
                // (nella classe che dichiara "id" o in una delle super classi)
                "push " + node.classCallSTEntry.offset, // pusho l'offset di "id" all'interno del frame di dichiarazione di "id" (che è il frame che ho appena caricato)
                "add", // sommo l'offset all'indirizzo del frame di dichiarazione di "id" per ottenere l'indirizzo di "id"
                "lw",  // carico l'indirizzo di "id" (che è l'indirizzo della dispatch table)
                "stm", // salvo il dispatch pointer in $tm
                "ltm", // carico il dispatch pointer
                "ltm", // duplico il dispatch pointer
                "lw",  // carico l'indirizzo della dispatch table
                "push " + node.classCallmethodSTEntry.offset, "add", // sommo l'offset di "id" all'indirizzo della dispatch table per ottenere l'indirizzo di "id"
                "lw", // carico l'indirizzo di "id" (che è l'indirizzo della funzione "id")
                "js"  // salto all'indirizzo della funzione "id"
        );
    }

    @Override
    public String visitNode(EmptyNode node) {
        if (print) printNode(node);
        return "push -1";
    }
}
