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
%column
%line

TERMINADOR_DE_LINHA  = \r|\n|\r\n
CARACTER_ENTRADA     = [^\r\n]
ESPACAMENTO          = {TERMINADOR_DE_LINHA} | [ \t\f]
PONTO = [.]
VIRGULA = [,]
PONTO_VIRGULA = [;]


NUM_INT = 0|[:digit:][:digit:]*
NUM_REAL = [:digit:][:digit:]*{PONTO}[:digit:][:digit:]*
OPSOMA = "+"
OPSUB = "-"
OPMUL = "*"
OPDIV = "/"
AP = "("
FP = ")"
OP_MENOR = "<" 
OP_MAIOR = ">" 
OP_MENOR_IGUAL = "<=" 
OP_MAIOR_IGUAL = ">="
OP_ATRI = "="
OP_IGUAL = "=="
OP_DIF = "!="


PALAVRA_RESERVADA = (if|then|else|do|while|for|var|begin|end|div|and|not|true|false|program|int|boolean|read|write|procedure)
IDENTIFICADOR = [:jletter:][:jletterdigit:]*

%%

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
{PALAVRA_RESERVADA}         { adicionarToken("PALAVRA_RESERVADA", yytext(), yyline, yycolumn); }
{IDENTIFICADOR}             { adicionarToken("IDENTIFICADOR", yytext(), yyline, yycolumn); }
{TERMINADOR_DE_LINHA}       {}
{CARACTER_ENTRADA}          {}
{ESPACAMENTO}               {}
{PONTO}                     {}
{VIRGULA}                   {}
{PONTO_VIRGULA}             {}

. { adicionarToken("ERRO", yytext(), yyline, yycolumn); }