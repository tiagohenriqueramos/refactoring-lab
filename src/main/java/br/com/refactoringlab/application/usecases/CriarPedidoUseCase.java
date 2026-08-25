package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.CriarPedidoInput;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.ports.PedidoRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CriarPedidoUseCase {

    private final PedidoRepositoryPort pedidoRepositoryPort;

    public CriarPedidoUseCase(PedidoRepositoryPort pedidoRepositoryPort) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
    }

    public Pedido executar(CriarPedidoInput input) {
        Pedido pedido = new Pedido();
        pedido.setCodigoInterno(input.codigoInterno());
        pedido.setClienteGuid(input.clienteGuid());
        pedido.setClienteNome(input.clienteNome());
        pedido.setCodigoRoteiro(input.codigoRoteiro());
        pedido.setCodigoRastreio(input.codigoRastreio());
        pedido.setNumeroNotaFiscal(input.numeroNotaFiscal());
        pedido.setChaveNfe(input.chaveNfe());
        pedido.setStatusPedido(input.statusPedido());
        pedido.setStatusUltimaOcorrencia(input.statusUltimaOcorrencia());
        pedido.setNomeDestinatario(input.nomeDestinatario());
        pedido.setCpfCnpjDestinatario(input.cpfCnpjDestinatario());
        pedido.setEnderecoDestinatario(input.enderecoDestinatario());
        pedido.setNomeRemetente(input.nomeRemetente());
        pedido.setCpfCnpjRemetente(input.cpfCnpjRemetente());
        pedido.setEnderecoRemetente(input.enderecoRemetente());
        pedido.setCodigoCentroDistribuicao(input.codigoCentroDistribuicao());
        pedido.setNomeCentroDistribuicao(input.nomeCentroDistribuicao());
        pedido.setEnderecoCentroDistribuicao(input.enderecoCentroDistribuicao());
        pedido.setDataPrometidaEntrega(input.dataPrometidaEntrega());

        // Regras de negocio de inicializacao
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setDataUltimoStatus(LocalDateTime.now());
        pedido.setQuantidadeTentativasEntrega(0);

        return pedidoRepositoryPort.salvar(pedido);
    }
}

