package lexico;

import java.util.ArrayList;

%%

%{
private ArrayList<Token> tokens;

private void adicionarToken(String descricao, String lexema) {
    this.tokens.add(new Token(lexema + " => " + descricao));
}

lexicalAnalyzer(java.io.Reader in){
    this.tokens = new ArrayList();
    this.zzReader = in;
}

%}


%class LexicalAnalyzer
%type void
%column
%line

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

%%

{PONTO}				{}
{BRANCO}            {}
{NUM_INT}           { adicionarToken("NUM_INT", yytext()); }
{NUM_REAL}          { adicionarToken("NUM_REAL", yytext()); }
{OPSOMA}            { adicionarToken("OPSOMA", yytext()); }
{OPSUB}             { adicionarToken("OPSUB", yytext()); }
{OPMUL}             { adicionarToken("OPMUL", yytext()); }
{OPDIV}             { adicionarToken("OPDIV", yytext()); }
{AP}                { adicionarToken("AP", yytext()); }
{FP}                { adicionarToken("FP", yytext()); }

. { adicionarToken("ERRO", yytext()); }