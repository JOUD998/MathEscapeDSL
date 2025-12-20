// Generated from src/grammar/QuestDSL.g4 by ANTLR 4.13.1
package grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QuestDSLParser}.
 */
public interface QuestDSLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QuestDSLParser#quest}.
	 * @param ctx the parse tree
	 */
	void enterQuest(QuestDSLParser.QuestContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestDSLParser#quest}.
	 * @param ctx the parse tree
	 */
	void exitQuest(QuestDSLParser.QuestContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuestDSLParser#step}.
	 * @param ctx the parse tree
	 */
	void enterStep(QuestDSLParser.StepContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestDSLParser#step}.
	 * @param ctx the parse tree
	 */
	void exitStep(QuestDSLParser.StepContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuestDSLParser#npc}.
	 * @param ctx the parse tree
	 */
	void enterNpc(QuestDSLParser.NpcContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestDSLParser#npc}.
	 * @param ctx the parse tree
	 */
	void exitNpc(QuestDSLParser.NpcContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuestDSLParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAction(QuestDSLParser.ActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestDSLParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAction(QuestDSLParser.ActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuestDSLParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(QuestDSLParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestDSLParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(QuestDSLParser.ConditionContext ctx);
}