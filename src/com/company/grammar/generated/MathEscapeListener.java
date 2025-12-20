// Generated from src/com/company/grammar/generated/MathEscape.g4 by ANTLR 4.13.1
package com.company.grammar.generated;
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
}