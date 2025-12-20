package grammar;

import org.json.JSONObject;

public class QuestListener extends QuestDSLBaseListener {

    private JSONObject json = new JSONObject();

    @Override
    public void enterQuest(QuestDSLParser.QuestContext ctx) {
        json.put("quest", ctx.STRING().getText().replace("\"", ""));
    }

    public JSONObject getJson() {
        return json;
    }
}