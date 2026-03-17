// Generated from src/com/company/grammar/MathDSL.g4 by ANTLR 4.13.1
package com.company.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MathDSLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MathDSLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(MathDSLParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MathDSLParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#funDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDecl(MathDSLParser.FunDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#letDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetDecl(MathDSLParser.LetDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(MathDSLParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(MathDSLParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(MathDSLParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(MathDSLParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnit(MathDSLParser.UnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MathDSLParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(MathDSLParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(MathDSLParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathDSLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MathDSLParser.PrimaryContext ctx);
}