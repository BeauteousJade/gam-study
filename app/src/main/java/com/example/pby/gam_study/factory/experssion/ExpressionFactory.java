package com.example.pby.gam_study.factory.experssion;

public class ExpressionFactory {

    public static ExpressionFragment createExpressionFragment(ExpressionFragment.OnExpressionClickListener listener) {
        ExpressionFragment expressionFragment = ExpressionFragment.newInstance();
        expressionFragment.setOnExpressionClickListener(listener);
        return expressionFragment;
    }
}
