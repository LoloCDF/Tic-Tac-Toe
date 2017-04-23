################################
# Client compilation           #
################################

compclient:
	javac -cp lib/axis-ant.jar:lib/commons-logging-1.0.4.jar:lib/axis.jar:lib/jaxrpc.jar:lib/saaj.jar:lib/commons-discovery-0.2.jar:lib/log4j-1.2.8.jar:lib/wsdl4j-1.5.1.jar:lib/mail.jar:lib/activation.jar -d build src/TicTacToe/*.java
	jar cvfe TicTacToe.jar TicTacToe.Play -C build/ TicTacToe

################################
# Client execution             #
################################

execlient:
	java -cp lib/axis-ant.jar:lib/commons-logging-1.0.4.jar:lib/axis.jar:lib/jaxrpc.jar:lib/saaj.jar:lib/commons-discovery-0.2.jar:lib/log4j-1.2.8.jar:lib/wsdl4j-1.5.1.jar:lib/mail.jar:lib/activation.jar:TicTacToe.jar TicTacToe.Play $(ARGS)

