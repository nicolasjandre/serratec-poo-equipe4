package com.serratec.domain.repository;

import com.serratec.domain.files.ArquivoTxt;
import com.serratec.domain.settings.Conexao;
import com.serratec.domain.settings.DadosConexao;
import com.serratec.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainRepository {
    private static final String PATH = "/home/nicolas/";
    private static final String SFILE = "DadosConexao.ini";
    private static final String BD = "equipe4-trabalhofinalpoo";
    public static final String SCHEMA = "sistema";
    public static Conexao CONEXAO;

    private static boolean createBD(String bd, String schema, DadosConexao dadosCon) {
        boolean bdCriado = false;

        if (criarDatabase(CONEXAO, bd)) {

            if (criarSchema(CONEXAO, schema)) {
                criarEntidadeCliente(CONEXAO, schema);
                criarEntidadeCategoria(CONEXAO, schema);
                criarEntidadeProduto(CONEXAO, schema);
                criarEntidadePedido(CONEXAO, schema);
                criarEntidadePeditem(CONEXAO, schema);

                bdCriado = true;
            }
        }

        return bdCriado;
    }

    private static Conexao conectar(String bd, DadosConexao dadosCon) {
        dadosCon.setBanco(bd);
        Conexao conexao = new Conexao(dadosCon);
        conexao.connect();
        return conexao;
    }

    private static boolean criarDatabase(Conexao con, String bd) {
        boolean bdExiste;
        int tentativas = 1;
        String sql;

        class Database {
            public static ResultSet Exists(Conexao con, String bd) {
                ResultSet entidade;
                String sql = "select datname from pg_database where datname = '" + bd + "'";
                entidade = con.query(sql);
                return entidade;
            }
        }

        do {
            try {
                bdExiste = Database.Exists(con, bd).next();

                if (!bdExiste) {
                    sql = "create database "+ bd;
                    con.query(sql);
                    tentativas++;
                }
            } catch (Exception e) {
                System.err.printf("Não foi possível criar o database %s: %s", bd, e);
                e.printStackTrace();
                return false;
            }
        } while (!bdExiste && (tentativas<=3));

        return bdExiste;
    }

    private static boolean criarSchema(Conexao con, String schema) {
        boolean schemaExiste;
        int tentativas = 1;
        String sql;

        class Schema {
            public static ResultSet Exists(Conexao con, String schema) {
                ResultSet entidade;
                String sql = "select * from pg_namespace where nspname = '" + schema + "'";
                entidade = con.query(sql);
                return entidade;
            }
        }

        do {
            try {
                schemaExiste = Schema.Exists(con, schema).next();

                if (!schemaExiste) {
                    sql = "create schema "+ schema;
                    con.query(sql);
                    tentativas++;
                }
            } catch (Exception e) {
                System.err.printf("Não foi possível criar o schema %s: %s", schema, e);
                e.printStackTrace();
                return false;
            }
        } while (!schemaExiste && (tentativas<=3));

        return schemaExiste;
    }

    private static void criarTabela(Conexao con, String entidade, String schema) {
        String sql = "create table " + schema + "." + entidade + " ()";
        con.query(sql);
    }

    private static void criarCampo(Conexao con, String schema, String entidade,
                                   String atributo, String tipoAtributo, boolean primario,
                                   boolean estrangeiro, String entidadeEstrangeira,
                                   String atributoEstrangeiro) {

        if (!atributoExists(con, schema, entidade, atributo)) {
            String sql = "alter table " + schema + "." + entidade + " add column " +
                    atributo + " " + tipoAtributo + " ";

            if (primario) {
                sql += "primary key ";
            }

            if (estrangeiro) {
                sql += "references " + entidadeEstrangeira + "(" + atributoEstrangeiro + ")";
            }

            con.query(sql);
        }
    }

    private static boolean atributoExists(Conexao con, String schema,
                                         String entidade, String atributo) {

        boolean atributoExist = false;

        String sql = "select table_schema, table_name, column_name from information_schema.columns "
                + "where table_schema = '" + schema + "' "
                + "and table_name = '" + entidade + "' "
                + "and column_name = '" + atributo + "'";

        ResultSet result = con.query(sql);

        try {
            atributoExist = (result.next());

        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
        }

        return atributoExist;
    }

    private static boolean entidadeExists(Conexao con, String schema, String entidade) {
        boolean entidadeExist = false;
        String sql =
                "SELECT n.nspname AS schemaname, c.relname AS tablename " +
                        "FROM pg_class c " +
                        "LEFT JOIN pg_namespace n ON n.oid = c.relnamespace " +
                        "LEFT JOIN pg_tablespace t ON t.oid = c.reltablespace " +
                        "WHERE c.relkind = 'r' " +
                        "AND n.nspname = '" + schema + "' " +
                        "AND c.relname = '" + entidade + "'";


        try {
            ResultSet tabela = con.query(sql);

            entidadeExist = (tabela.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entidadeExist;
    }

    private static void criarEntidadeCliente(Conexao con, String schema) {
        String entidade = "cliente";

        if (!entidadeExists(con, schema, entidade))
            criarTabela(con, entidade, schema);

        if (entidadeExists(con, schema, entidade)) {
            criarCampo(con, schema, entidade, "idcliente", "serial",
                    true,  false, null, null);
            criarCampo(con, schema, entidade, "nome", "varchar(100)",
                    false, false, null, null);
            criarCampo(con, schema, entidade, "cpf", "varchar(11)" ,
                    false, false, null, null);
            criarCampo(con, schema, entidade, "dtnascimento", "timestamp" ,
                    false, false, null, null);
            criarCampo(con, schema, entidade, "endereco" , "varchar(150)",
                    false, false, null, null);
            criarCampo(con, schema, entidade, "telefone", "varchar(20)",
                    false, false, null, null);
        }
    }

    private static void criarEntidadePedido(Conexao con, String schema) {
        String entidade = "pedido";

        if (!entidadeExists(con, schema, entidade))
            criarTabela(con, entidade, schema);

        if (entidadeExists(con, schema, entidade)) {
            criarCampo(con, schema, entidade, "idpedido", "serial", true,  false,
                    null, null);
            criarCampo(con, schema, entidade, "dtemissao", "timestamp", false, false,
                    null, null);
            criarCampo(con, schema, entidade, "dtentrega", "timestamp", false, false,
                    null, null);
            criarCampo(con, schema, entidade, "valortotal", "double precision" , false, false,
                    null, null);
            criarCampo(con, schema, entidade, "observacao", "varchar(256)" , false, false,
                    null, null);
            criarCampo(con, schema, entidade, "idcliente", "integer" , false, true,
                    schema+".cliente", "idcliente");
        }
    }

    private static void criarEntidadeCategoria(Conexao con, String schema) {
        String entidade = "categoria";

        if (!entidadeExists(con, schema, entidade))
            criarTabela(con, entidade, schema);

        if (entidadeExists(con, schema, entidade)) {
            criarCampo(con, schema, entidade, "idcategoria", "serial",true,  false,
                    null, null);
            criarCampo(con, schema, entidade, "descricao", "varchar(100)", false, false,
                    null, null);
        }
    }

    private static void criarEntidadeProduto(Conexao con, String schema) {
        String entidade = "produto";

        if (!entidadeExists(con, schema, entidade))
            criarTabela(con, entidade, schema);

        if (entidadeExists(con, schema, entidade)) {
            criarCampo(con, schema, entidade, "idproduto", "serial", true,  false,
                    null, null);
            criarCampo(con, schema, entidade, "descricao", "varchar(100)", false, false,
                    null, null);
            criarCampo(con, schema, entidade, "idcategoria", "integer" , false, true,
                    schema+".categoria", "idcategoria");
            criarCampo(con, schema, entidade, "estoque", "double precision", false, false,
                    null, null);
            criarCampo(con, schema, entidade, "vlcusto", "double precision", false, false,
                    null, null);
            criarCampo(con, schema, entidade, "vlvenda", "double precision" , false, false,
                    null, null);
        }
    }

    private static void criarEntidadePeditem(Conexao con, String schema) {
        String entidade = "peditem";

        if (!entidadeExists(con, schema, entidade))
            criarTabela(con, entidade, schema);

        if (entidadeExists(con, schema, entidade)) {
            criarCampo(con, schema, entidade, "idpeditem", "serial", true,  false,
                    null, null);
            criarCampo(con, schema, entidade, "idpedido", "integer", false, true,
                    schema+".pedido", "idpedido");
            criarCampo(con, schema, entidade, "idproduto", "integer", false, true,
                    schema+".produto", "idproduto");
            criarCampo(con, schema, entidade, "vlunitario", "double precision" , false,
                    false, null, null);
            criarCampo(con, schema, entidade, "vldesconto", "double precision" , false,
                    false, null, null);
            criarCampo(con, schema, entidade, "quantidade", "double precision" , false,
                    false, null, null);
        }
    }

    public static void iniciarConexaoComBanco() {
        ArquivoTxt conexaoIni = new ArquivoTxt(PATH + SFILE);
        DadosConexao dadoCon = new DadosConexao();
        boolean abrirSistema = false;
        Scanner input = new Scanner(System.in);


        if (conexaoIni.criarArquivo()) {
            if (conexaoIni.alimentaDadosConexao()) {
                dadoCon = conexaoIni.getData();
                abrirSistema = true;
            } else {
                conexaoIni.apagarArquivo();
                System.out.println("Arquivo de configuração de conexão:\n");
                System.out.println("Local: ");
                String local = input.nextLine();
                System.out.println("Porta: ");
                String porta = input.nextLine();
                System.out.println("Usuário: ");
                String usuario = input.nextLine();
                System.out.println("Senha: ");
                String senha = input.nextLine();
                System.out.println("Database: ");
                String banco = input.nextLine();

                if (conexaoIni.criarArquivo()) {
                    conexaoIni.escreverArquivo("bd=PostgreSql");
                    conexaoIni.escreverArquivo("local=" + local);
                    conexaoIni.escreverArquivo("porta=" + porta);
                    conexaoIni.escreverArquivo("usuario=" + usuario);
                    conexaoIni.escreverArquivo("senha=" + senha);
                    conexaoIni.escreverArquivo("banco=" + banco);

                    if (conexaoIni.alimentaDadosConexao()) {
                        dadoCon = conexaoIni.getData();
                        abrirSistema = true;
                    } else System.out.println("Não foi possível efetuar a configuração.\nVerifique");
                }
            }
        } else {
            System.out.println("Houve um problema na criação do arquivo de configuração.");
        }

        if (abrirSistema) {
            CONEXAO = conectar(BD, dadoCon);
            if (MainRepository.createBD(BD, SCHEMA, dadoCon)) {
                Util.imprimirSistemaIniciado();
            } else {
                System.err.println("Houve um problema na criação do banco de dados.");
            }
        }
    }
}
