################################
# Client compilation           #
################################

compclient:
	javac -cp lib/axis-ant.jar:lib/commons-logging-1.0.4.jar:lib/axis.jar:lib/jaxrpc.jar:lib/saaj.jar:lib/commons-discovery-0.2.jar:lib/log4j-1.2.8.jar:lib/wsdl4j-1.5.1.jar:lib/mail.jar:lib/activation.jar:../common/common.jar -d build src/tictactoe/*.java
	jar cvfe client.jar tictactoe.Play -C build/ tictactoe

################################
# Client execution             #
################################

execlient:
	java -cp lib/axis-ant.jar:lib/commons-logging-1.0.4.jar:lib/axis.jar:lib/jaxrpc.jar:lib/saaj.jar:lib/commons-discovery-0.2.jar:lib/log4j-1.2.8.jar:lib/wsdl4j-1.5.1.jar:lib/mail.jar:lib/activation.jar:../common/common.jar:client.jar tictactoe.Play $(ARGS)

