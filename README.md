# Getting Started

### Reference Documentation
This is a simple Spring AI RAG example. It uses 

* Java 17 or above
* Spring Boot 3.5.6
* Spring AI 1.0.3
* Spring Web
* Spring Web flux for reative support
* Postgres as Vector DB (via Docker)

### Configuration
The following need be configured 

* app.vector.load-on-start-up (true/false)
* app.vector.load-on-strat-up (if you want to load the file defined by the path in app.vector.pdf.file-path when application starts up)


### Setting this project up

1. Run Postgress PGVector via  ankane/pgvector:latest
   * Use the SQL below to set up the table 
 '
CREATE EXTENSION IF NOT EXISTS hstore;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store (
id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
content text,
metadata json,
embedding vector(768)
);

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);'


2. Use the configuration properties in previous section
3. AI Models:
   * ai/gemma3 [I ran via docker models]
   * nomic-ai/nomic-embed-text-v1.5-GGUF [I ran via LM Studio]
4. Finally run this application using usual maven spring boot run command or as you like
5. http://localhost:8080/home makes you land on a chat page where you post your questions to get the answers based on the documents you have already added.
   *  Change IP/hostname if you want to access from a different device as you need
   * Also, your Posgtress and Model configs may be different . Change them dilligently 
 