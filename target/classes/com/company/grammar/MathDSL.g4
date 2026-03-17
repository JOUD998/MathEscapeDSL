grammar MathDSL;

// ==================
// Parser rules
// ==================

prog
    : statement* EOF
    ;

// --- Statements ---
statement
    : funDecl SEMI
    | letDecl SEMI
    | exprStmt SEMI
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
    : expr (COMMA expr)* /// x,x,x // 1,1,1
    ;

// --- Units ---
unit
    : ID (DIV ID)?
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
    : primary POWER factor
    | primary
    ;

primary
    : NUMBER unit?              // 3.5, 3.5 m, 200 ms, 2 km/h (parsed as km / h)
    | ID LPAREN argList? RPAREN // function call: f(...)
    | ID                        // variable reference
    | LPAREN expr RPAREN
    | IF expr THEN expr ELSE expr
    ;

// ==================
// Lexer rules
// ==================

// Keywords
FUN   : 'fun';
LET   : 'let';

// Control flow
IF   : 'if';
THEN : 'then';
ELSE : 'else';


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
SEMI : ';' ;



// Whitespace
WS    : [ \t\r\n]+ -> skip ;