CREATE TABLE ordem_servico(
    ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    ID_VEICULO BIGINT not null,
    STATUS varchar(50) not null,
    DATA_FECHAMENTO timestamptz,
    VALOR_TOTAL numeric(10,2) default 0 not null,
    FOREIGN KEY(ID_VEICULO) REFERENCES veiculo(ID)
)