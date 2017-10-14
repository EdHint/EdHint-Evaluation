import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static int getOffsetForLine(javax.swing.text.Document arg0, int arg1) {
        return (Integer) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("javax.swing.text")
                 .addDeclaringClasses(javax.swing.text.Document.class)
                 .addArgument(javax.swing.text.Document.class, "arg0", arg0)
                 .addArgument(int.class, "arg1", arg1)
                 .setReturnType(int.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test() throws Throwable {
         java.lang.String html = " <html>\n"
                    + "   <head>\n"
                    + "     <title>An example HTMLDocument</title>\n"
                    + "     <style type=\"text/css\">\n"
                    + "       div { background-color: silver; }\n"
                    + "       ul { color: red; }\n"
                    + "     </style>\n"
                    + "   </head>\n"
                    + "   <body>\n"
                    + "     <div id=\"BOX\">\n"
                    + "       <p>Paragraph 1</p>\n"
                    + "       <p>Paragraph 2</p>\n"
                    + "     </div>\n"
                    + "   </body>\n"
                    + " </html>\n";
         
        java.io.Reader stringReader = new java.io.StringReader(html);
        javax.swing.text.html.HTMLEditorKit htmlKit = new javax.swing.text.html.HTMLEditorKit();
        javax.swing.text.html.HTMLDocument htmlDoc = (javax.swing.text.html.HTMLDocument) htmlKit.createDefaultDocument();
        htmlKit.read(stringReader, htmlDoc, 0);
         
        int lineNum = getOffsetForLine(htmlDoc, 1);
        if(lineNum == 3)
            return true;
        else
            return false;
    }
     

    public static void main(String[] args) {
        int maxTime = Integer.parseInt(args[0]);
        SketchExecutor.setType(ExecutorType.JUZI);
        boolean found = false;
        boolean timeout = false;
        int i = 0;
        long begin = System.currentTimeMillis();
        long checkpoint = 0;
        do {
            if(System.currentTimeMillis() - begin >= maxTime * 1000) {
                timeout = true;
                break;
            }
            i ++;
            if(System.currentTimeMillis() - checkpoint >= 5000) {
                System.out.println(i + " executions tried!");
                checkpoint = System.currentTimeMillis();
            }
            request.reset();
            try {
                if(!test()) {
                    throw new RuntimeException("****TEST FAILURE");
                }
                found = true;
                break;
            } catch (Throwable e) {
               // e.printStackTrace();
            }
        } while (SketchExecutor.incrementCounter());
        if(found) {
            System.out.println("Found solution:");
            System.out.println("----------------------------------------------------------------");
            System.out.println(request.getCandidateBatchInvocation(0));
            System.out.println("----------------------------------------------------------------");
        } else if(timeout) {
            System.out.println("Timeout! Max execution time: " + maxTime + " seconds.");
        } else {
            System.out.println("No solution!");
        }
        
        System.out.println("Number of invocations tried: " + request.getNumBatchInvocationsTried(0));
        System.out.println("Search depth: " + request.getCurrentSearchDepth(0));
        System.out.println("Number of executions: " + i);
    }

}
