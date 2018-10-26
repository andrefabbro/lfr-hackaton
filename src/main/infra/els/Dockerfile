FROM docker.elastic.co/elasticsearch/elasticsearch:6.1.3

RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install analysis-icu \
    && /usr/share/elasticsearch/bin/elasticsearch-plugin install analysis-kuromoji \
    && /usr/share/elasticsearch/bin/elasticsearch-plugin install analysis-smartcn \
    && /usr/share/elasticsearch/bin/elasticsearch-plugin install analysis-stempel

COPY --chown=elasticsearch:elasticsearch elasticsearch.yml /usr/share/elasticsearch/config/