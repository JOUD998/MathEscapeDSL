grammar MathEscape;

// ==================
// Parser rules
// ==================

mathEscape
    : statement
    ;

statement
    : ID '=' expr
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
    : postfix POWER factor   // right-associative
    | postfix
    ;

postfix
    : primary FACT?          // factorial أعلى شيء
    ;

primary
    : INT
    | ID
    | '(' expr ')'
    ;

// ==================
// Lexer rules
// ==================

ID    : [a-zA-Z]+ ;
INT   : [0-9]+ ;
PLUS  : '+' ;
MINUS : '-' ;
MULTI : '*' ;
DIV   : '/' ;
FACT  : '!' ;
POWER : '^' ;
WS    : [ \t\r\n]+ -> skip ;