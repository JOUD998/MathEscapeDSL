grammar MathEscape;

// Parser rules
mathEscape
    : expr '=' expr
    ;
w
expr
    : expr PLUS term
    | expr MINUS term
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
EQUAL : '=' ;
WS  : [ \t\r\n]+ -> skip ;