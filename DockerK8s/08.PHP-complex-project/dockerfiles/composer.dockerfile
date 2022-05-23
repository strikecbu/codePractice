FROM composer:2.3.5

WORKDIR /var/www/html

ENTRYPOINT ["composer", "--ignore-platform-reqs"]
