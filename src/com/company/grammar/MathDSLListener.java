// Generated from src/com/company/grammar/MathDSL.g4 by ANTLR 4.13.1
package com.company.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MathDSLParser}.
 */
public interface MathDSLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(MathDSLParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(MathDSLParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MathDSLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MathDSLParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#funDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunDecl(MathDSLParser.FunDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#funDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunDecl(MathDSLParser.FunDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#letDecl}.
	 * @param ctx the parse tree
	 */
	void enterLetDecl(MathDSLParser.LetDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#letDecl}.
	 * @param ctx the parse tree
	 */
	void exitLetDecl(MathDSLParser.LetDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(MathDSLParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(MathDSLParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(MathDSLParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(MathDSLParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(MathDSLParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(MathDSLParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(MathDSLParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(MathDSLParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#unit}.
	 * @param ctx the parse tree
	 */
	void enterUnit(MathDSLParser.UnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#unit}.
	 * @param ctx the parse tree
	 */
	void exitUnit(MathDSLParser.UnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MathDSLParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MathDSLParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(MathDSLParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(MathDSLParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(MathDSLParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(MathDSLParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathDSLParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(MathDSLParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathDSLParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(MathDSLParser.PrimaryContext ctx);
}