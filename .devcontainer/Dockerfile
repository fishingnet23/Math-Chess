# Start from the original universal image you had
FROM mcr.microsoft.com/devcontainers/universal:2

USER root

# Clean out the expired legacy Yarn keys that cause the apt-get update failure
RUN rm -f /etc/apt/sources.list.d/yarn.list \
    && rm -f /usr/share/keyrings/yarn.gpg \
    && apt-get update
