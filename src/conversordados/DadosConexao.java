package conversordados;
public class DadosConexao {

	private String usuario;
	private String senha;
	private int tipoBanco;
	private int tipoBancoClient;
        
        private String servidor;
        private String porta;
        private String bancoServico;
        
        private String tabela;
        
        private String nomeSchema;
        
        private String condicao = "";

        public DadosConexao() { }
        
	public void setUsuario(String usuario) {
           this.usuario = usuario;
	}
	public String getUsuario() {
		return this.usuario;
	}

	public void setSenha(String senha) {
              this.senha = senha;
	}
	public String getSenha() {
		return this.senha;
	}

	public void setTipoBanco(int tipoBanco) {
            this.tipoBanco = tipoBanco;
	}
	public int getTipoBanco() {
		return this.tipoBanco;
	}

	public void setTipoBancoClient(int tipoBancoClient) {
            this.tipoBancoClient = tipoBancoClient;
	}
	public int getTipoBancoClient() {
		return this.tipoBancoClient;
	}
        
        public void setServidor(String servidor) {
            this.servidor = servidor;
	}
	public String getServidor() {
		return this.servidor;
	}
        
        public void setPorta(String porta) {
            this.porta = porta;
	}
	public String getPorta() {
		return this.porta;
	}

        public void setBancoServico(String bancoServico) {
            this.bancoServico = bancoServico;
	}
	public String getBancoServico() {
		return this.bancoServico; 
	}
        
        public void setSchema(String nomeSchema) {
            this.nomeSchema = nomeSchema;
	}
	public String getSchema() {
		return this.nomeSchema; 
	}
        
        public void setTabela(String tabela) {
            this.tabela = tabela;
	}
	public String getTabela() {
		return this.tabela; 
	}


        public String getCondicao() {
            return condicao;
        }


        public void setCondicao(String condicao) {
            this.condicao = condicao;
        }
}
