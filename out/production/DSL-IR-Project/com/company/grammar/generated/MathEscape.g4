grammar MathEscape;

// Parser rules
mathEscape
    : expr '=' expr
    ;

expr
    : expr PLUS term
    | expr MINUS term
    | expr MULTI term
    | expr DIV term
    | term
    ;

term
    : INT
    | ID
    ;

// Lexer rules
ID  : [a-zA-Z]+ ;
INT : [0-9]+ ;
PLUS : '+' ;
MINUS : '-' ;
MULTI : '*';
DIV : '/';
EQUAL : '=' ;
WS  : [ \t\r\n]+ -> skip ;