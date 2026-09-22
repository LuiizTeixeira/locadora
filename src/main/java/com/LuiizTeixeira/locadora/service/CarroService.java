package com.LuiizTeixeira.locadora.service;

import com.LuiizTeixeira.locadora.entity.CarroEntity;
import com.LuiizTeixeira.locadora.model.exception.EntityNotFoundException;
import com.LuiizTeixeira.locadora.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    private final CarroRepository Repository;

    public CarroService(CarroRepository carroRepository) {
        this.Repository = carroRepository;
    }

    public CarroEntity salvar(CarroEntity carro) {
        if (carro.getValorDiaria() <= 0) {
            throw new IllegalArgumentException("O valor da diária deve ser maior que zero.");
        }
        return Repository.save(carro);
    }

    public CarroEntity atualizar(long id, CarroEntity carroAtualizado) {
        var carroExistente = Repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(" Carro não encontratdo."));

        carroExistente.setAno(carroAtualizado.getAno());
        carroExistente.setModelo(carroAtualizado.getModelo());
        carroExistente.setValorDiaria(carroAtualizado.getValorDiaria());
        return Repository.save(carroExistente);
    }

    public void deletar(long id) {
        var carroExistente = Repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado."));

        Repository.delete(carroExistente);
    }

    public CarroEntity buscarPorId(long id) {
        return Repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado."));
    }

    public List<CarroEntity> buscarTodos() {
        return Repository.findAll();
    }

    public List<CarroEntity> listarTodos() {
        return null;
    }
}
