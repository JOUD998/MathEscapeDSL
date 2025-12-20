package grammar;

import grammar.QuestDSLBaseListener;

public class QuestPrinterListener extends QuestDSLBaseListener {

    @Override
    public void enterQuest(QuestDSLParser.QuestContext ctx) {
        System.out.println("Quest name: " + ctx.STRING().getText());
        System.out.println("Joud");
    }
    @Override
    public void enterStep(QuestDSLParser.StepContext ctx) {
        System.out.println("  Step: " + ctx.STRING().getText());
    }

    @Override
    public void enterNpc(QuestDSLParser.NpcContext ctx) {
        System.out.println("    NPC: " + ctx.STRING().getText());
    }

    @Override
    public void enterAction(QuestDSLParser.ActionContext ctx) {
        System.out.println("    Action: " + ctx.STRING().getText());
    }


}
