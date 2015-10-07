package vn.hiworld.com.chloe.util;

import java.io.File;
import java.util.function.Consumer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Runner {

  private static final String MONGO_EXAMPLES_DIR = "mongo-examples";
  private static final String MONGO_EXAMPLES_JAVA_DIR = MONGO_EXAMPLES_DIR + "/src/main/java/";
  private static final String MONGO_EXAMPLES_JS_DIR = MONGO_EXAMPLES_DIR + "/src/main/js/";

  public static void runExample(Class clazz) {
    Runner.runJavaExample(MONGO_EXAMPLES_JAVA_DIR, clazz, false);
  }

  public static void runJSExample(String scriptName) {
    Runner.runScriptExample(MONGO_EXAMPLES_JS_DIR, "io/vertx/examples/mongo/mongo_client_verticle.js", false);
  }
  public static void runJavaExample(String prefix, Class clazz, boolean clustered) {
	    runJavaExample(prefix, clazz, new VertxOptions().setClustered(clustered));
	  }

	  public static void runJavaExample(String prefix, Class clazz, VertxOptions options) {
	    String exampleDir = prefix + clazz.getPackage().getName().replace(".", "/");
	    runExample(exampleDir, clazz.getName(), options);
	  }

	  public static void runJavaExample(String prefix, Class clazz, DeploymentOptions deploymentOptions) {
	    String exampleDir = prefix + clazz.getPackage().getName().replace(".", "/");
	    runExample(exampleDir, clazz.getName(), new VertxOptions(), deploymentOptions);
	  }

	  public static void runScriptExample(String prefix, String scriptName, boolean clustered) {
	    File file = new File(scriptName);
	    String dirPart = file.getParent();
	    String scriptDir = prefix + dirPart;
	    Runner.runExample(scriptDir, scriptDir + "/" + file.getName(), clustered);
	  }

	  public static void runScriptExample(String prefix, String scriptName, VertxOptions options) {
	    File file = new File(scriptName);
	    String dirPart = file.getParent();
	    String scriptDir = prefix + dirPart;
	    Runner.runExample(scriptDir, scriptDir + "/" + file.getName(), options);
	  }

	  public static void runExample(String exampleDir, String verticleID, boolean clustered) {
	    runExample(exampleDir, verticleID, new VertxOptions().setClustered(clustered));
	  }

	  public static void runExample(String exampleDir, String verticleID, VertxOptions options) {
	    runExample(exampleDir, verticleID, options, null);
	  }

	  public static void runExample(String exampleDir, String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
	    System.setProperty("vertx.cwd", exampleDir);
	    
	    Consumer<Vertx> runner = vertx -> {
	      try {
	        if (deploymentOptions != null) {
	          vertx.deployVerticle(verticleID, deploymentOptions);
	        } else {
	          vertx.deployVerticle(verticleID);
	        }
	      } catch (Throwable t) {
	        t.printStackTrace();
	      }
	    };
	    
	    
	    if (options.isClustered()) {
	      Vertx.clusteredVertx(options, res -> {
	        if (res.succeeded()) {
	          Vertx vertx = res.result();
	          runner.accept(vertx);
	        } else {
	          res.cause().printStackTrace();
	        }
	      });
	    } else {
	      Vertx vertx = Vertx.vertx(options);
	      runner.accept(vertx);
	    }
	  }


}