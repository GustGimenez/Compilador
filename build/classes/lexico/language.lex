package lexico;

import java_cup.runtime.Symbol;

%%


%cup
%public
%type java_cup.runtime.Symbol
%class LexicalAnalyzer
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

	{VIRGULA}                   {}
	{PONTO_VIRGULA}             {}
	{DOIS_PONTOS}               {}
	{BRANCO}                    {}

	{palavra_begin}             { return new Symbol(Sym.palavra_begin); }
	{palavra_end}               { return new Symbol(Sym.palavra_end); }
	{palavra_if}                { return new Symbol(Sym.palavra_if); }
	{palavra_then}              { return new Symbol(Sym.palavra_then); }
	{palavra_else}              { return new Symbol(Sym.palavra_else); }
	{palavra_do}                { return new Symbol(Sym.palavra_do); }
	{palavra_while}             { return new Symbol(Sym.palavra_while); }
	{palavra_var}               { return new Symbol(Sym.palavra_var); }
	{palavra_procedure}         { return new Symbol(Sym.palavra_procedure); }
	{palavra_program}           { return new Symbol(Sym.palavra_program); }
	{palavra_and}               { return new Symbol(Sym.palavra_and); }
	{palavra_not}               { return new Symbol(Sym.palavra_not); }
	{palavra_or}                { return new Symbol(Sym.palavra_or );}

	{tipo_int}                  { return new Symbol(Sym.tipo_int); }
	{tipo_boolean}              { return new Symbol(Sym.tipo_boolean); }

	{NUM_INT}                   { return new Symbol(Sym.NUM_INT); }
	{NUM_REAL}                  { return new Symbol(Sym.NUM_REAL); }
	{OPSOMA}                    { return new Symbol(Sym.OPSOMA); }
	{OPSUB}                     { return new Symbol(Sym.OPSUB); }
	{OPMUL}                     { return new Symbol(Sym.OPMUL); }
	{OPDIV}                     { return new Symbol(Sym.OPDIV); }
	{AP}                        { return new Symbol(Sym.AP); }
	{FP}                        { return new Symbol(Sym.FP); }
	{OP_MENOR}                  { return new Symbol(Sym.OP_MENOR); }
	{OP_MAIOR}                  { return new Symbol(Sym.OP_MAIOR); }
	{OP_MENOR_IGUAL}            { return new Symbol(Sym.OP_MENOR_IGUAL); }
	{OP_MAIOR_IGUAL}            { return new Symbol(Sym.OP_MAIOR_IGUAL); }
	{OP_ATRI}                   { return new Symbol(Sym.OP_ATRI); }
	{OP_IGUAL}                  { return new Symbol(Sym.OP_IGUAL); }
	{OP_DIF}                    { return new Symbol(Sym.OP_DIF); }
	{IDENTIFICADOR}             { return new Symbol(Sym.IDENTIFICADOR); }

	"." { return new Symbol(Sym.FIM);}
}

. { return new Symbol(Sym.DESCONHECIDO);}