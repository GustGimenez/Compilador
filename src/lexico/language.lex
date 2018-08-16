package lexico;

import java.util.ArrayList;

%%

%{
private ArrayList<Token> tokens;

private void adicionarToken(String classificacao, String lexema) {
    this.tokens.add(new Token(classificacao, lexema));
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

LETRA = [a-zA-Z]
UL = "_"
PONTO = [.]
DIGITO = [0-9]
BRANCO = [\n| |\t|\r]
NUM_INT = 0|{DIGITO}{DIGITO}*
NUM_REAL = {DIGITO}{DIGITO}*{PONTO}{DIGITO}{DIGITO}*
OPSOMA = "+"
OPSUB = "-"
OPMUL = "*"
OPDIV = "/"
AP = "("
FP = ")"
PALAVRA_RESERVADA = (if|then|else|do|while|for|var|begin|end|div|and|not|true|false|program|int|boolean|read|write|procedure)
OP_MENOR = "<" 
OP_MAIOR = ">" 
OP_MENOR_IGUAL = "<=" 
OP_MAIOR_IGUAL = ">="
IDENTIFICADOR = ({LETRA}|{UL}{LETRA})({LETRA}|{DIGITO})*

%%

{PONTO}		    {}
{BRANCO}            {}
{LETRA}             {}
{UL}                {}
{NUM_INT}           { adicionarToken("NUM_INT", yytext()); }
{NUM_REAL}          { adicionarToken("NUM_REAL", yytext()); }
{OPSOMA}            { adicionarToken("OPSOMA", yytext()); }
{OPSUB}             { adicionarToken("OPSUB", yytext()); }
{OPMUL}             { adicionarToken("OPMUL", yytext()); }
{OPDIV}             { adicionarToken("OPDIV", yytext()); }
{AP}                { adicionarToken("AP", yytext()); }
{FP}                { adicionarToken("FP", yytext()); }
{PALAVRA_RESERVADA} { adicionarToken("PALAVRA_RESERVADA", yytext()); }
{OP_MENOR}          { adicionarToken("OP_MENOR", yytext()); }
{OP_MAIOR}          { adicionarToken("OP_MAIOR", yytext()); }
{OP_MENOR_IGUAL}    { adicionarToken("OP_MENOR_IGUAL", yytext()); }
{OP_MAIOR_IGUAL}    { adicionarToken("OP_MAIOR_IGUAL", yytext()); }
{IDENTIFICADOR}     { adicionarToken("IDENTIFICADOR", yytext()); }

. { adicionarToken("ERRO", yytext()); }