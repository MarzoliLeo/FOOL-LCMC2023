// Generated from D:/Users/Xmachines/Desktop/FOOL-LCMC2023/FOOL-main/src/compiler\FOOL.g4 by ANTLR 4.12.0
package compiler;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class FOOLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, TIMES=3, DIV=4, LPAR=5, RPAR=6, CLPAR=7, CRPAR=8, SEMIC=9, 
		COLON=10, COMMA=11, DOT=12, OR=13, AND=14, NOT=15, GE=16, LE=17, EQ=18, 
		ASS=19, TRUE=20, FALSE=21, IF=22, THEN=23, ELSE=24, PRINT=25, LET=26, 
		IN=27, VAR=28, FUN=29, CLASS=30, EXTENDS=31, NEW=32, NULL=33, INT=34, 
		BOOL=35, NUM=36, ID=37, WHITESP=38, COMMENT=39, ERR=40;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PLUS", "MINUS", "TIMES", "DIV", "LPAR", "RPAR", "CLPAR", "CRPAR", "SEMIC", 
			"COLON", "COMMA", "DOT", "OR", "AND", "NOT", "GE", "LE", "EQ", "ASS", 
			"TRUE", "FALSE", "IF", "THEN", "ELSE", "PRINT", "LET", "IN", "VAR", "FUN", 
			"CLASS", "EXTENDS", "NEW", "NULL", "INT", "BOOL", "NUM", "ID", "WHITESP", 
			"COMMENT", "ERR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'('", "')'", "'{'", "'}'", "';'", 
			"':'", "','", "'.'", "'||'", "'&&'", "'!'", "'>='", "'<='", "'=='", "'='", 
			"'true'", "'false'", "'if'", "'then'", "'else'", "'print'", "'let'", 
			"'in'", "'var'", "'fun'", "'class'", "'extends'", "'new'", "'null'", 
			"'int'", "'bool'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PLUS", "MINUS", "TIMES", "DIV", "LPAR", "RPAR", "CLPAR", "CRPAR", 
			"SEMIC", "COLON", "COMMA", "DOT", "OR", "AND", "NOT", "GE", "LE", "EQ", 
			"ASS", "TRUE", "FALSE", "IF", "THEN", "ELSE", "PRINT", "LET", "IN", "VAR", 
			"FUN", "CLASS", "EXTENDS", "NEW", "NULL", "INT", "BOOL", "NUM", "ID", 
			"WHITESP", "COMMENT", "ERR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public int lexicalErrors=0;


	public FOOLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FOOL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 39:
			ERR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ERR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 System.out.println("Invalid char: "+ getText() +" at line "+getLine()); lexicalErrors++; 
			break;
		}
	}

	public static final String _serializedATN =
		"\u0004\u0000(\u00f4\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0001 \u0001 \u0001 \u0001 \u0001 \u0001!\u0001!\u0001"+
		"!\u0001!\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001#\u0001#\u0001#"+
		"\u0005#\u00cd\b#\n#\f#\u00d0\t#\u0003#\u00d2\b#\u0001$\u0001$\u0005$\u00d6"+
		"\b$\n$\f$\u00d9\t$\u0001%\u0004%\u00dc\b%\u000b%\f%\u00dd\u0001%\u0001"+
		"%\u0001&\u0001&\u0001&\u0001&\u0005&\u00e6\b&\n&\f&\u00e9\t&\u0001&\u0001"+
		"&\u0001&\u0001&\u0001&\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\u00e7"+
		"\u0000(\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b"+
		"\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b"+
		"\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016"+
		"-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f? A!C\""+
		"E#G$I%K&M\'O(\u0001\u0000\u0003\u0002\u0000AZaz\u0003\u000009AZaz\u0003"+
		"\u0000\t\n\r\r  \u00f8\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003"+
		"\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007"+
		"\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001"+
		"\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000"+
		"\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000"+
		"\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000"+
		"\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000"+
		"\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000"+
		"\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000"+
		"\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000"+
		")\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001"+
		"\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000"+
		"\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u0000"+
		"7\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001"+
		"\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000"+
		"\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000"+
		"E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001"+
		"\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M\u0001\u0000\u0000"+
		"\u0000\u0000O\u0001\u0000\u0000\u0000\u0001Q\u0001\u0000\u0000\u0000\u0003"+
		"S\u0001\u0000\u0000\u0000\u0005U\u0001\u0000\u0000\u0000\u0007W\u0001"+
		"\u0000\u0000\u0000\tY\u0001\u0000\u0000\u0000\u000b[\u0001\u0000\u0000"+
		"\u0000\r]\u0001\u0000\u0000\u0000\u000f_\u0001\u0000\u0000\u0000\u0011"+
		"a\u0001\u0000\u0000\u0000\u0013c\u0001\u0000\u0000\u0000\u0015e\u0001"+
		"\u0000\u0000\u0000\u0017g\u0001\u0000\u0000\u0000\u0019i\u0001\u0000\u0000"+
		"\u0000\u001bl\u0001\u0000\u0000\u0000\u001do\u0001\u0000\u0000\u0000\u001f"+
		"q\u0001\u0000\u0000\u0000!t\u0001\u0000\u0000\u0000#w\u0001\u0000\u0000"+
		"\u0000%z\u0001\u0000\u0000\u0000\'|\u0001\u0000\u0000\u0000)\u0081\u0001"+
		"\u0000\u0000\u0000+\u0087\u0001\u0000\u0000\u0000-\u008a\u0001\u0000\u0000"+
		"\u0000/\u008f\u0001\u0000\u0000\u00001\u0094\u0001\u0000\u0000\u00003"+
		"\u009a\u0001\u0000\u0000\u00005\u009e\u0001\u0000\u0000\u00007\u00a1\u0001"+
		"\u0000\u0000\u00009\u00a5\u0001\u0000\u0000\u0000;\u00a9\u0001\u0000\u0000"+
		"\u0000=\u00af\u0001\u0000\u0000\u0000?\u00b7\u0001\u0000\u0000\u0000A"+
		"\u00bb\u0001\u0000\u0000\u0000C\u00c0\u0001\u0000\u0000\u0000E\u00c4\u0001"+
		"\u0000\u0000\u0000G\u00d1\u0001\u0000\u0000\u0000I\u00d3\u0001\u0000\u0000"+
		"\u0000K\u00db\u0001\u0000\u0000\u0000M\u00e1\u0001\u0000\u0000\u0000O"+
		"\u00ef\u0001\u0000\u0000\u0000QR\u0005+\u0000\u0000R\u0002\u0001\u0000"+
		"\u0000\u0000ST\u0005-\u0000\u0000T\u0004\u0001\u0000\u0000\u0000UV\u0005"+
		"*\u0000\u0000V\u0006\u0001\u0000\u0000\u0000WX\u0005/\u0000\u0000X\b\u0001"+
		"\u0000\u0000\u0000YZ\u0005(\u0000\u0000Z\n\u0001\u0000\u0000\u0000[\\"+
		"\u0005)\u0000\u0000\\\f\u0001\u0000\u0000\u0000]^\u0005{\u0000\u0000^"+
		"\u000e\u0001\u0000\u0000\u0000_`\u0005}\u0000\u0000`\u0010\u0001\u0000"+
		"\u0000\u0000ab\u0005;\u0000\u0000b\u0012\u0001\u0000\u0000\u0000cd\u0005"+
		":\u0000\u0000d\u0014\u0001\u0000\u0000\u0000ef\u0005,\u0000\u0000f\u0016"+
		"\u0001\u0000\u0000\u0000gh\u0005.\u0000\u0000h\u0018\u0001\u0000\u0000"+
		"\u0000ij\u0005|\u0000\u0000jk\u0005|\u0000\u0000k\u001a\u0001\u0000\u0000"+
		"\u0000lm\u0005&\u0000\u0000mn\u0005&\u0000\u0000n\u001c\u0001\u0000\u0000"+
		"\u0000op\u0005!\u0000\u0000p\u001e\u0001\u0000\u0000\u0000qr\u0005>\u0000"+
		"\u0000rs\u0005=\u0000\u0000s \u0001\u0000\u0000\u0000tu\u0005<\u0000\u0000"+
		"uv\u0005=\u0000\u0000v\"\u0001\u0000\u0000\u0000wx\u0005=\u0000\u0000"+
		"xy\u0005=\u0000\u0000y$\u0001\u0000\u0000\u0000z{\u0005=\u0000\u0000{"+
		"&\u0001\u0000\u0000\u0000|}\u0005t\u0000\u0000}~\u0005r\u0000\u0000~\u007f"+
		"\u0005u\u0000\u0000\u007f\u0080\u0005e\u0000\u0000\u0080(\u0001\u0000"+
		"\u0000\u0000\u0081\u0082\u0005f\u0000\u0000\u0082\u0083\u0005a\u0000\u0000"+
		"\u0083\u0084\u0005l\u0000\u0000\u0084\u0085\u0005s\u0000\u0000\u0085\u0086"+
		"\u0005e\u0000\u0000\u0086*\u0001\u0000\u0000\u0000\u0087\u0088\u0005i"+
		"\u0000\u0000\u0088\u0089\u0005f\u0000\u0000\u0089,\u0001\u0000\u0000\u0000"+
		"\u008a\u008b\u0005t\u0000\u0000\u008b\u008c\u0005h\u0000\u0000\u008c\u008d"+
		"\u0005e\u0000\u0000\u008d\u008e\u0005n\u0000\u0000\u008e.\u0001\u0000"+
		"\u0000\u0000\u008f\u0090\u0005e\u0000\u0000\u0090\u0091\u0005l\u0000\u0000"+
		"\u0091\u0092\u0005s\u0000\u0000\u0092\u0093\u0005e\u0000\u0000\u00930"+
		"\u0001\u0000\u0000\u0000\u0094\u0095\u0005p\u0000\u0000\u0095\u0096\u0005"+
		"r\u0000\u0000\u0096\u0097\u0005i\u0000\u0000\u0097\u0098\u0005n\u0000"+
		"\u0000\u0098\u0099\u0005t\u0000\u0000\u00992\u0001\u0000\u0000\u0000\u009a"+
		"\u009b\u0005l\u0000\u0000\u009b\u009c\u0005e\u0000\u0000\u009c\u009d\u0005"+
		"t\u0000\u0000\u009d4\u0001\u0000\u0000\u0000\u009e\u009f\u0005i\u0000"+
		"\u0000\u009f\u00a0\u0005n\u0000\u0000\u00a06\u0001\u0000\u0000\u0000\u00a1"+
		"\u00a2\u0005v\u0000\u0000\u00a2\u00a3\u0005a\u0000\u0000\u00a3\u00a4\u0005"+
		"r\u0000\u0000\u00a48\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005f\u0000"+
		"\u0000\u00a6\u00a7\u0005u\u0000\u0000\u00a7\u00a8\u0005n\u0000\u0000\u00a8"+
		":\u0001\u0000\u0000\u0000\u00a9\u00aa\u0005c\u0000\u0000\u00aa\u00ab\u0005"+
		"l\u0000\u0000\u00ab\u00ac\u0005a\u0000\u0000\u00ac\u00ad\u0005s\u0000"+
		"\u0000\u00ad\u00ae\u0005s\u0000\u0000\u00ae<\u0001\u0000\u0000\u0000\u00af"+
		"\u00b0\u0005e\u0000\u0000\u00b0\u00b1\u0005x\u0000\u0000\u00b1\u00b2\u0005"+
		"t\u0000\u0000\u00b2\u00b3\u0005e\u0000\u0000\u00b3\u00b4\u0005n\u0000"+
		"\u0000\u00b4\u00b5\u0005d\u0000\u0000\u00b5\u00b6\u0005s\u0000\u0000\u00b6"+
		">\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005n\u0000\u0000\u00b8\u00b9\u0005"+
		"e\u0000\u0000\u00b9\u00ba\u0005w\u0000\u0000\u00ba@\u0001\u0000\u0000"+
		"\u0000\u00bb\u00bc\u0005n\u0000\u0000\u00bc\u00bd\u0005u\u0000\u0000\u00bd"+
		"\u00be\u0005l\u0000\u0000\u00be\u00bf\u0005l\u0000\u0000\u00bfB\u0001"+
		"\u0000\u0000\u0000\u00c0\u00c1\u0005i\u0000\u0000\u00c1\u00c2\u0005n\u0000"+
		"\u0000\u00c2\u00c3\u0005t\u0000\u0000\u00c3D\u0001\u0000\u0000\u0000\u00c4"+
		"\u00c5\u0005b\u0000\u0000\u00c5\u00c6\u0005o\u0000\u0000\u00c6\u00c7\u0005"+
		"o\u0000\u0000\u00c7\u00c8\u0005l\u0000\u0000\u00c8F\u0001\u0000\u0000"+
		"\u0000\u00c9\u00d2\u00050\u0000\u0000\u00ca\u00ce\u000219\u0000\u00cb"+
		"\u00cd\u000209\u0000\u00cc\u00cb\u0001\u0000\u0000\u0000\u00cd\u00d0\u0001"+
		"\u0000\u0000\u0000\u00ce\u00cc\u0001\u0000\u0000\u0000\u00ce\u00cf\u0001"+
		"\u0000\u0000\u0000\u00cf\u00d2\u0001\u0000\u0000\u0000\u00d0\u00ce\u0001"+
		"\u0000\u0000\u0000\u00d1\u00c9\u0001\u0000\u0000\u0000\u00d1\u00ca\u0001"+
		"\u0000\u0000\u0000\u00d2H\u0001\u0000\u0000\u0000\u00d3\u00d7\u0007\u0000"+
		"\u0000\u0000\u00d4\u00d6\u0007\u0001\u0000\u0000\u00d5\u00d4\u0001\u0000"+
		"\u0000\u0000\u00d6\u00d9\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001\u0000"+
		"\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000\u0000\u00d8J\u0001\u0000\u0000"+
		"\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00da\u00dc\u0007\u0002\u0000"+
		"\u0000\u00db\u00da\u0001\u0000\u0000\u0000\u00dc\u00dd\u0001\u0000\u0000"+
		"\u0000\u00dd\u00db\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000"+
		"\u0000\u00de\u00df\u0001\u0000\u0000\u0000\u00df\u00e0\u0006%\u0000\u0000"+
		"\u00e0L\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005/\u0000\u0000\u00e2\u00e3"+
		"\u0005*\u0000\u0000\u00e3\u00e7\u0001\u0000\u0000\u0000\u00e4\u00e6\t"+
		"\u0000\u0000\u0000\u00e5\u00e4\u0001\u0000\u0000\u0000\u00e6\u00e9\u0001"+
		"\u0000\u0000\u0000\u00e7\u00e8\u0001\u0000\u0000\u0000\u00e7\u00e5\u0001"+
		"\u0000\u0000\u0000\u00e8\u00ea\u0001\u0000\u0000\u0000\u00e9\u00e7\u0001"+
		"\u0000\u0000\u0000\u00ea\u00eb\u0005*\u0000\u0000\u00eb\u00ec\u0005/\u0000"+
		"\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed\u00ee\u0006&\u0000\u0000"+
		"\u00eeN\u0001\u0000\u0000\u0000\u00ef\u00f0\t\u0000\u0000\u0000\u00f0"+
		"\u00f1\u0006\'\u0001\u0000\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u00f3"+
		"\u0006\'\u0000\u0000\u00f3P\u0001\u0000\u0000\u0000\u0006\u0000\u00ce"+
		"\u00d1\u00d7\u00dd\u00e7\u0002\u0000\u0001\u0000\u0001\'\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}