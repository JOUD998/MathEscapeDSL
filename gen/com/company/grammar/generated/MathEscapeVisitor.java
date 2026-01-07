// Generated from /Users/joudnassar/Developer/HSBI/DSL-IR-Project/src/com/company/grammar/generated/MathEscape.g4 by ANTLR 4.13.2
package com.company.grammar.generated;
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
}