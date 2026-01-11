// Generated from src/com/company/generated_Grammars/MathEscape.g4 by ANTLR 4.13.1
package com.company.generated_Grammars;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MathEscapeParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MathEscapeVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MathEscapeParser#mathEscape}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMathEscape(MathEscapeParser.MathEscapeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathEscapeParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MathEscapeParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathEscapeParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MathEscapeParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathEscapeParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(MathEscapeParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathEscapeParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(MathEscapeParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathEscapeParser#postfix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix(MathEscapeParser.PostfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathEscapeParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MathEscapeParser.PrimaryContext ctx);
}