FROM mcr.microsoft.com/devcontainers/universal:2

USER root

RUN rm -f /etc/apt/sources.list.d/yarn.list \
    && rm -f /usr/share/keyrings/yarn.gpg \
    && apt-get update
