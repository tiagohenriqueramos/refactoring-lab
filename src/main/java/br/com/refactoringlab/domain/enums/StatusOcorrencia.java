package br.com.refactoringlab.domain.enums;

public enum StatusOcorrencia {

    RECEBIDO_CD("Recebido no centro de distribuição") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return StatusPedido.RECEBIDO;
        }
    },
    ENTREGUE("Entrega realizada com sucesso") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return StatusPedido.ENTREGUE;
        }
    },
    INSUCESSO_ENDERECO_NAO_ENCONTRADO("Endereço não encontrado") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return resolverStatusInsucesso(tentativasAtuais);
        }
    },
    INSUCESSO_AUSENTE("Cliente ausente") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return resolverStatusInsucesso(tentativasAtuais);
        }
    },
    INSUCESSO_RECUSADO("Entrega recusada pelo destinatário") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return resolverStatusInsucesso(tentativasAtuais);
        }
    },
    DEVOLUCAO("Pedido devolvido ao remetente") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return StatusPedido.DEVOLVIDO;
        }
    },
    EXTRAVIO("Extravio de carga") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return StatusPedido.SINISTRO;
        }
    },
    AVARIA("Avaria no produto") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return StatusPedido.SINISTRO;
        }
    },
    FURTADO("Carga furtada") {
        @Override
        public StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais) {
            return StatusPedido.SINISTRO;
        }
    };

    private static final int MAX_TENTATIVAS = 3;
    private final String descricao;

    StatusOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Método abstrato implementado por cada constante para definir o próximo status do pedido.
     */
    public abstract StatusPedido calcularProximoStatus(StatusPedido statusAtual, int tentativasAtuais);

    /**
     * Lógica reutilizável para tratamento de insucesso de entrega
     */
    protected StatusPedido resolverStatusInsucesso(int tentativasAtuais) {
        if (tentativasAtuais + 1 >= MAX_TENTATIVAS) {
            return StatusPedido.EM_RETORNO;
        }
        return StatusPedido.EM_TRANSITO;
    }
}