import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    static User user;
    static ArrayList<Message> messages = new ArrayList<>();

    public static void main(String[] args) {
        Spark.init();
        Spark.get("/",
                (request, response) -> {
                    HashMap<String, Object> m = new HashMap();
                    if(user == null){
                        return new ModelAndView(m, "index.html");
                    }else{
                        m.put("name", user.name);
                        m.put("message", messages);
                        return new ModelAndView(m, "messages.html");
                    }
                },
                new MustacheTemplateEngine());

        Spark.post("/login", (request, response) -> {
            String name = request.queryParams("loginName");
            user = new User(name);
            response.redirect("/");
            return "";
        });

        Spark.post("/index", (request, response) -> {
            String text = request.queryParams("message");
            Message message = new Message(text);
            messages.add(message);
            response.redirect("/");
            return "";
        });
    }
}


