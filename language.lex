package lexicalanalyzer;

%%

%{

private void imprimir(String descricao, String lexema) {
    System.out.println(lexema + " => " + descricao);
}

%}


%class LexicalAnalyzer
%type void

PONTO = [.]
DIGITO = [0-9]
BRANCO = [\n| |\t|\r]
NUM_INT = 0|{DIGITO}{DIGITO}*
NUM_REAL = {DIGITO}{PONTO}{DIGITO}+
OPSOMA = "+"
OPSUB = "-"
OPMUL = "*"
OPDIV = "/"
AP = "("
FP = ")"

%%

{PONTO}				{}
{BRANCO}            {}
{NUM_INT}           { imprimir("NUM_INT", yytext()); }
{NUM_REAL}          { imprimir("NUM_REAL", yytext()); }
{OPSOMA}            { imprimir("OPSOMA", yytext()); }
{OPSUB}             { imprimir("OPSUB", yytext()); }
{OPMUL}             { imprimir("OPMUL", yytext()); }
{OPDIV}             { imprimir("OPDIV", yytext()); }
{AP}                { imprimir("AP", yytext()); }
{FP}                { imprimir("FP", yytext()); }

. { imprimir("ERRO", yytext()); }