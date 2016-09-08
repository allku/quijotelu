# .bash_profile

# Get the aliases and functions
if [ -f ~/.bashrc ]; then
	. ~/.bashrc
fi

# User specific environment and startup programs

PATH=$PATH:$HOME/bin

export PATH

#Java
JAVA_HOME=/usr/java/jdk1.7.0_71; export JAVA_HOME
PATH=/usr/java/jdk1.7.0_71/bin/:$PATH; export PATH

