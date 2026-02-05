grammar MathDSL;

// ==================
// Parser rules
// ==================

prog
    : statement EOF
    ;

statement
    : FUN ID '(' paramList ')' ':' unit EQUAL expr
    | LET ID (':'unit)? EQUAL INT unit?
    ;

paramList
    : param (',' param)*
    ;

param
    : ID ':' unit
    ;

unit
    :  baseUnit
    |  baseUnit '/' baseUnit
    ;
baseUnit
    :  UNIT_TIME
    |  UNIT_LENGTH
    |  UNIT_MASS
    ;
expr
    : expr PLUS term
    | expr MINUS term
    | term
    ;

term
    : term MULTI factor
    | term DIV factor
    | factor
    ;

factor
    : postfix POWER factor
    | postfix
    ;

postfix
    : primary FACT?
    ;

primary
    : INT
    | ID
    | '(' expr ')'
    ;


// ==================
// Lexer rules
// ==================

FUN   : 'fun';
LET   : 'let';
UNIT_TIME    : 'ms' | 's' | 'h';
UNIT_LENGTH  : 'cm' | 'm' | 'km';
UNIT_MASS    : 'g' | 'kg';
ID    : [a-zA-Z]+ ;
INT   : [0-9]+ ;
EQUAL : '=' ;
PLUS  : '+' ;
MINUS : '-' ;
MULTI : '*' ;
DIV   : '/' ;
FACT  : '!' ;
POWER : '^' ;
WS    : [ \t\r\n]+ -> skip ;
