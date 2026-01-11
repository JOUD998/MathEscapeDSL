// Generated from src/com/company/generated_Grammars/MathEscape.g4 by ANTLR 4.13.1
package com.company.generated_Grammars;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MathEscapeParser}.
 */
public interface MathEscapeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MathEscapeParser#mathEscape}.
	 * @param ctx the parse tree
	 */
	void enterMathEscape(MathEscapeParser.MathEscapeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathEscapeParser#mathEscape}.
	 * @param ctx the parse tree
	 */
	void exitMathEscape(MathEscapeParser.MathEscapeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathEscapeParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MathEscapeParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathEscapeParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MathEscapeParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathEscapeParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MathEscapeParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathEscapeParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MathEscapeParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathEscapeParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(MathEscapeParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathEscapeParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(MathEscapeParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathEscapeParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(MathEscapeParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathEscapeParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(MathEscapeParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathEscapeParser#postfix}.
	 * @param ctx the parse tree
	 */
	void enterPostfix(MathEscapeParser.PostfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathEscapeParser#postfix}.
	 * @param ctx the parse tree
	 */
	void exitPostfix(MathEscapeParser.PostfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathEscapeParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(MathEscapeParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathEscapeParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(MathEscapeParser.PrimaryContext ctx);
}