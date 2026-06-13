package br.ifg.urt.shieldnoterpgbox.service;


import br.ifg.urt.shieldnoterpgbox.dto.request.UserRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.UserResponse;
import br.ifg.urt.shieldnoterpgbox.exception.BusinessException;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.mapper.UserMapper;
import br.ifg.urt.shieldnoterpgbox.model.User;
import br.ifg.urt.shieldnoterpgbox.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse registrar(UserRequest request) {
        if (request.senha() == null || request.senha().isBlank())
            throw new BusinessException("Senha é obrigatória no registro");
        if (userRepository.existsByEmail(request.email()))
            throw new BusinessException("Email já cadastrado: " + request.email());

        
        User user = new User();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setSenhaHash(request.senha()); 

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    

    @Transactional(readOnly = true)
    public List<UserResponse> listarTodos() {
        return userMapper.toResponseDTOList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UserResponse buscarPorId(UUID id) {
        return userMapper.toResponseDTO(findById(id));
    }

    @Transactional
    public UserResponse atualizar(UUID id, UserRequest request) {
        User user = findById(id);
        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email()))
            throw new BusinessException("Email já em uso: " + request.email());
            
        user.setNome(request.nome());
        user.setEmail(request.email());
        
        if (request.senha() != null && !request.senha().isBlank()) {
            user.setSenhaHash(request.senha()); // 
        }
            
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Transactional
    public void deletar(UUID id) { userRepository.delete(findById(id)); }

    private User findById(UUID id) {
        
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User não encontrado com id: " + id));
    }
}