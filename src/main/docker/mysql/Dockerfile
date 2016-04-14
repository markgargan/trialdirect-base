FROM ubuntu:15.04
MAINTAINER Mark Gargan <mark[DOT]gargan[at]tekenable[DOT]com>

RUN apt-get update

RUN apt-get -y install mysql-client mysql-server
RUN sed -i -e"s/^bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" /etc/mysql/mysql.conf.d/mysqld.cnf

ADD ./startup.sh /opt/startup.sh
RUN chmod +x /opt/startup.sh
EXPOSE 3306

CMD ["/bin/bash", "/opt/startup.sh"]