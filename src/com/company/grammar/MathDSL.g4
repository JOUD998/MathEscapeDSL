grammar MathDSL;

// ==================
// Parser rules
// ==================

prog
    : statement* EOF
    ;

// --- Statements ---
statement
    : funDecl
    | letDecl
    | exprStmt
    ;

funDecl
    : FUN fname=ID LPAREN paramList? RPAREN (COLON unit)? EQUAL expr
    ;

letDecl
    : LET vname=ID (COLON unit)? EQUAL expr
    ;

exprStmt
    : expr
    ;

// --- Parameters & Arguments ---
paramList
    : param (COMMA param)*
    ;

param
    : pname=ID (COLON unit)?
    ;

argList
    : expr (COMMA expr)*
    ;

// --- Units ---
unit
    : baseUnit
    | baseUnit DIV baseUnit
    ;

baseUnit
    : UNIT_TIME
    | UNIT_LENGTH
    | UNIT_MASS
    ;

// --- Expressions (precedence) ---
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
    : postfix POWER factor      // right-assoc power
    | postfix
    ;

postfix
    : primary FACT?             // optional factorial
    ;

primary
    : NUMBER unit?              // 3.5, 3.5 m, 200 ms, 2 km/h (parsed as km / h)
    | ID LPAREN argList? RPAREN // function call: f(...)
    | ID                        // variable reference
    | LPAREN expr RPAREN
    ;

// ==================
// Lexer rules
// ==================

// Keywords
FUN   : 'fun';
LET   : 'let';

// Units (must come before ID)
UNIT_TIME    : 'ms' | 's' | 'h';
UNIT_LENGTH  : 'cm' | 'm' | 'km';
UNIT_MASS    : 'g' | 'kg';

// Tokens
NUMBER : [0-9]+ ('.' [0-9]+)?;
ID     : [a-zA-Z]+ ;

// Punctuation
LPAREN : '(';
RPAREN : ')';
COMMA  : ',';
COLON  : ':';

// Operators
EQUAL : '=' ;
PLUS  : '+' ;
MINUS : '-' ;
MULTI : '*' ;
DIV   : '/' ;
FACT  : '!' ;
POWER : '^' ;

// Whitespace
WS    : [ \t\r\n]+ -> skip ;