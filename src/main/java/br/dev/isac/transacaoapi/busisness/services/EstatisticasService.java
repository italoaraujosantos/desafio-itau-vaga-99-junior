package br.dev.isac.transacaoapi.busisness.services;

import br.dev.isac.transacaoapi.controller.dtos.EstatisticasResponseDTO;
import br.dev.isac.transacaoapi.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstatisticasTransaccoes(Integer intervalorBusca) {
       List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervalorBusca);

       DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream().mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();

       return new EstatisticasResponseDTO(
               estatisticasTransacoes.getCount(),
               estatisticasTransacoes.getSum(),
               estatisticasTransacoes.getAverage(),
               estatisticasTransacoes.getMin(),
               estatisticasTransacoes.getMax()
       );
    }

}
