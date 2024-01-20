package mk.ukim.finki.wp.june2022.g1.service.impl;

import mk.ukim.finki.wp.june2022.g1.model.OSType;
import mk.ukim.finki.wp.june2022.g1.model.User;
import mk.ukim.finki.wp.june2022.g1.model.VirtualServer;
import mk.ukim.finki.wp.june2022.g1.model.exceptions.InvalidVirtualMachineIdException;
import mk.ukim.finki.wp.june2022.g1.repository.VirtualServerRepository;
import mk.ukim.finki.wp.june2022.g1.service.UserService;
import mk.ukim.finki.wp.june2022.g1.service.VirtualServerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirtualServerServiceImpl implements VirtualServerService {
    private final VirtualServerRepository virtualServerRepository;
    private final UserService userService;

    public VirtualServerServiceImpl(VirtualServerRepository virtualServerRepository, UserService userService) {
        this.virtualServerRepository = virtualServerRepository;
        this.userService = userService;
    }

    @Override
    public List<VirtualServer> listAll() {
        return this.virtualServerRepository.findAll();
    }

    @Override
    public VirtualServer findById(Long id) {
        return this.virtualServerRepository.findById(id).orElseThrow(InvalidVirtualMachineIdException::new);
    }

    @Override
    public VirtualServer create(String name, String ipAddress, OSType osType, List<Long> owners, LocalDate launchDate) {
        List<User> users=owners.stream().map(this.userService::findById).collect(Collectors.toList());
        return this.virtualServerRepository.save(new VirtualServer(name,ipAddress,osType,users,launchDate));
    }

    @Override
    public VirtualServer update(Long id, String name, String ipAddress, OSType osType, List<Long> owners) {
        VirtualServer virtualServer=findById(id);
        List<User> users=owners.stream().map(this.userService::findById).collect(Collectors.toList());
        virtualServer.setInstanceName(name);
        virtualServer.setIpAddress(ipAddress);
        virtualServer.setOSType(osType);
        virtualServer.setOwners(users);
        return this.virtualServerRepository.save(virtualServer);
    }

    @Override
    public VirtualServer delete(Long id) {
        VirtualServer virtualServer=findById(id);
        this.virtualServerRepository.delete(virtualServer);
        return virtualServer;
    }

    @Override
    public VirtualServer markTerminated(Long id) {
        VirtualServer virtualServer=findById(id);
        virtualServer.setTerminated(true);
       return this.virtualServerRepository.save(virtualServer);
    }

    @Override
    public List<VirtualServer> filter(Long ownerId, Integer activeMoreThanDays) {
        if (ownerId == null && activeMoreThanDays == null) {
            return listAll();
        } else if (ownerId != null && activeMoreThanDays != null) {
            User owner=this.userService.findById(ownerId);
            return this.virtualServerRepository.findByOwnersContainsAndLaunchDateBefore(owner, LocalDate.now().minusDays(activeMoreThanDays));
        } else if (ownerId != null) {
            User owner=this.userService.findById(ownerId);
            return this.virtualServerRepository.findByOwnersContains(owner);
        } else {
            return this.virtualServerRepository.findALLByLaunchDateBefore(LocalDate.now().minusDays(activeMoreThanDays));
        }
    }
}
