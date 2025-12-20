grammar QuestDSL;

quest: 'Quest' STRING '{' step+ '}';
step: 'Step' STRING '{' (npc | action | condition)* '}';
npc: 'NPC:' STRING;
action: 'Action:' STRING;
condition: 'Condition:' STRING;

STRING: '"' (~["\r\n])* '"';
WS: [ \t\r\n]+ -> skip;