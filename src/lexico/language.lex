package lexico;

 import java.util.ArrayList; 

%%

%{
    private ArrayList<Token> tokens;

     private void adicionarToken(String classificacao, String lexema, int linha, int coluna) {
        this.tokens.add(new Token(classificacao, lexema, linha, coluna));
    }

    public LexicalAnalyzer(java.io.Reader in){
        this.tokens = new ArrayList();
        this.zzReader = in;
    }

    public ArrayList getTokens(){
        return this.tokens;
    }

%}

%class LexicalAnalyzer
%type void
%public
%line
%column

BRANCO = [\n| |\t|\r]
PONTO = "."
VIRGULA = ","
PONTO_VIRGULA = ";"
DOIS_PONTOS = ":"



DIGITO = [0-9]
LETRA = [_|a-zA-Z]

NUM_INT = 0|{DIGITO}{DIGITO}*
NUM_REAL = {DIGITO}{DIGITO}*{PONTO}{DIGITO}{DIGITO}*
OPSOMA = "+"
OPSUB = "-"
OPMUL = "*"
OPDIV = "div"
AP = "("
FP = ")"
OP_MENOR = "<" 
OP_MAIOR = ">" 
OP_MENOR_IGUAL = "<=" 
OP_MAIOR_IGUAL = ">="
OP_ATRI = ":="
OP_IGUAL = "=="
OP_DIF = "<>"

palavra_true = "true"
palavra_false = "false"
palavra_read = "read"
palavra_write = "write"
palavra_begin = "begin"
palavra_end = "end"
palavra_if = "if" 
palavra_then = "then"
palavra_else = "else"
palavra_do = "do"
palavra_while = "while"
palavra_var = "var"
palavra_procedure = "procedure"
palavra_program = "program"
palavra_or = "or"
palavra_and = "and"
palavra_not = "not"
tipo_int = "int"
tipo_boolean = "boolean"

IDENTIFICADOR = {LETRA}({LETRA}|{DIGITO})*

%%

<YYINITIAL> {

	{BRANCO}                    {}
	{VIRGULA}                   {adicionarToken("VIRGULA", yytext(), yyline, yycolumn);}
	{PONTO_VIRGULA}             {adicionarToken("PONTO_VIRGULA", yytext(), yyline, yycolumn);}
	{DOIS_PONTOS}               {adicionarToken("DOIS_PONTOS", yytext(), yyline, yycolumn);}

	{palavra_false}             { adicionarToken("palavra_false", yytext(), yyline, yycolumn); }
	{palavra_true}              { adicionarToken("palavra_true", yytext(), yyline, yycolumn); }
	{palavra_begin}             { adicionarToken("palavra_begin", yytext(), yyline, yycolumn); }
	{palavra_read}              { adicionarToken("palavra_read", yytext(), yyline, yycolumn); }
	{palavra_write}             { adicionarToken("palavra_write", yytext(), yyline, yycolumn); }
	{palavra_end}               { adicionarToken("palavra_end", yytext(), yyline, yycolumn); }
	{palavra_if}                { adicionarToken("palavra_if", yytext(), yyline, yycolumn); }
	{palavra_then}              { adicionarToken("palavra_then", yytext(), yyline, yycolumn); }
	{palavra_else}              { adicionarToken("palavra_else", yytext(), yyline, yycolumn); }
	{palavra_do}                { adicionarToken("palavra_do", yytext(), yyline, yycolumn); }
	{palavra_while}             { adicionarToken("palavra_while", yytext(), yyline, yycolumn); }
	{palavra_var}               { adicionarToken("palavra_var", yytext(), yyline, yycolumn); }
	{palavra_procedure}         { adicionarToken("palavra_procedure", yytext(), yyline, yycolumn); }
	{palavra_program}           { adicionarToken("palavra_program", yytext(), yyline, yycolumn); }
	{palavra_and}               { adicionarToken("palavra_and", yytext(), yyline, yycolumn); }
	{palavra_not}               { adicionarToken("palavra_not", yytext(), yyline, yycolumn); }
	{palavra_or}                { adicionarToken("palavra_or", yytext(), yyline, yycolumn); }

	{tipo_int}                  { adicionarToken("tipo_int", yytext(), yyline, yycolumn); }
	{tipo_boolean}              { adicionarToken("tipo_boolean", yytext(), yyline, yycolumn); }

	{NUM_INT}                   { adicionarToken("NUM_INT", yytext(), yyline, yycolumn); }
	{NUM_REAL}                  { adicionarToken("NUM_REAL", yytext(), yyline, yycolumn); }
	{OPSOMA}                    { adicionarToken("OPSOMA", yytext(), yyline, yycolumn); }
	{OPSUB}                     { adicionarToken("OPSUB", yytext(), yyline, yycolumn); }
	{OPMUL}                     { adicionarToken("OPMUL", yytext(), yyline, yycolumn); }
	{OPDIV}                     { adicionarToken("OPDIV", yytext(), yyline, yycolumn); }
	{AP}                        { adicionarToken("AP", yytext(), yyline, yycolumn); }
	{FP}                        { adicionarToken("FP", yytext(), yyline, yycolumn); }
	{OP_MENOR}                  { adicionarToken("OP_MENOR", yytext(), yyline, yycolumn); }
	{OP_MAIOR}                  { adicionarToken("OP_MAIOR", yytext(), yyline, yycolumn); }
	{OP_MENOR_IGUAL}            { adicionarToken("OP_MENOR_IGUAL", yytext(), yyline, yycolumn); }
	{OP_MAIOR_IGUAL}            { adicionarToken("OP_MAIOR_IGUAL", yytext(), yyline, yycolumn); }
	{OP_ATRI}                   { adicionarToken("OP_ATRI", yytext(), yyline, yycolumn); }
	{OP_IGUAL}                  { adicionarToken("OP_IGUAL", yytext(), yyline, yycolumn); }
	{OP_DIF}                    { adicionarToken("OP_DIF", yytext(), yyline, yycolumn); }
	{IDENTIFICADOR}             { adicionarToken("IDENTIFICADOR", yytext(), yyline, yycolumn); }

	"." { adicionarToken("FIM", yytext(), yyline, yycolumn); }
}

. { adicionarToken("DESCONHECIDO", yytext(), yyline, yycolumn); }