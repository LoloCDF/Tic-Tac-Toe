##########################
# Preparar server        #
##########################

compserver:
	find src/ -name "*.java" -print | xargs javac -cp ../axis-1_4/lib/axis-ant.jar:../axis-1_4/lib/commons-logging-1.0.4.jar:../axis-1_4/lib/axis.jar:../axis-1_4/lib/jaxrpc.jar:../axis-1_4/lib/saaj.jar:../axis-1_4/lib/commons-discovery-0.2.jar:../axis-1_4/lib/log4j-1.2.8.jar:../axis-1_4/lib/wsdl4j-1.5.1.jar:../axis-1_4/lib/mail.jar:../axis-1_4/lib/activation.jar:../common/common.jar -d build
	jar cvf server.jar -C build/ tictactoe

##########################
# Deploy server          #
##########################
deployserver:
	cp deploy.wsdd ../axis-1_4
	java -Duser.dir=$(shell pwd)/../axis-1_4/ -cp lib/axis-ant.jar:lib/commons-logging-1.0.4.jar:lib/axis.jar:lib/jaxrpc.jar:lib/saaj.jar:lib/commons-discovery-0.2.jar:lib/log4j-1.2.8.jar:lib/wsdl4j-1.5.1.jar:lib/mail.jar:lib/activation.jar:../common/common.jar:../server/server.jar org.apache.axis.client.AdminClient -p 8888 deploy.wsdd

##########################
# Execute server         #
##########################

execserver:
	java -Duser.dir=$(shell pwd)/../axis-1_4/ -cp lib/axis-ant.jar:lib/commons-logging-1.0.4.jar:lib/axis.jar:lib/jaxrpc.jar:lib/saaj.jar:lib/commons-discovery-0.2.jar:lib/log4j-1.2.8.jar:lib/wsdl4j-1.5.1.jar:lib/mail.jar:lib/activation.jar:../common/common.jar:../server/server.jar org.apache.axis.transport.http.SimpleAxisServer -p 8888

##########################
# Execute TCPMon         #
##########################
tcpmon:
	java -Duser.dir=$(shell pwd)/../axis-1_4/ -cp lib/axis-ant.jar:lib/commons-logging-1.0.4.jar:lib/axis.jar:lib/jaxrpc.jar:lib/saaj.jar:lib/commons-discovery-0.2.jar:lib/log4j-1.2.8.jar:lib/wsdl4j-1.5.1.jar:lib/mail.jar:lib/activation.jar org.apache.axis.utils.tcpmon 9999 localhost 8888
