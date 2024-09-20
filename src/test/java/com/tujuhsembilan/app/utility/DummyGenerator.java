package com.tujuhsembilan.app.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tujuhsembilan.app.model.Client;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Position;
import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentRequest;
import com.tujuhsembilan.app.model.TalentRequestStatus;
import com.tujuhsembilan.app.model.TalentStatus;
import com.tujuhsembilan.app.model.TalentWishlist;
import com.tujuhsembilan.app.model.User;

public class DummyGenerator {
    
    private static DummyGenerator INSTANCE;
    private final ObjectMapper objectMapper;

    private final List<TalentStatus> talentStatuses;
    private final List<TalentLevel> talentLevels;
    private final List<EmployeeStatus> employeeStatuses;
    private final List<Skillset> skillsets;
    private final List<Position> positions;
    private final List<TalentRequestStatus> talentRequestStatuses;

    private final List<Talent> talents;
    private final List<User> users;
    private final List<Client> clientList;
    private final List<TalentWishlist> talentWishlists;
    private final List<TalentRequest> talentRequests;
    

    public DummyGenerator() {
        objectMapper = new ObjectMapper();

        employeeStatuses = generateEmployeeStatusList();
        talentLevels = generateTalentLevelList();
        talentStatuses = generateTalentStatusList();
        talentRequestStatuses = generateTalentRequestStatusList();
        positions = generatePositionList();
        skillsets = generateSkillsetList();

        users = generateUserList();
        clientList = generateClientList();
        talents = generateTalentList();
        talentWishlists = generateTalentWishlistList();
        talentRequests = generateTalentRequestList();

    }

    public List<TalentStatus> getTalentStatuses() {
        return this.talentStatuses;
    }

    public List<TalentLevel> getTalentLevels() {
        return this.talentLevels;
    }

    public List<EmployeeStatus> getEmployeeStatuses() {
        return this.employeeStatuses;
    }

    public List<Skillset> getSkillsets() {
        return this.skillsets;
    }

    public List<Position> getPositions() {
        return this.positions;
    }

    public List<TalentRequestStatus> getTalentRequestStatuses() {
        return this.talentRequestStatuses;
    }

    public List<Client> getClientList() {
        return this.clientList;
    }

    public List<TalentWishlist> getTalentWishlists() {
        return this.talentWishlists;
    }

    public List<TalentRequest> getTalentRequests() {
        return this.talentRequests;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Talent> getTalents() {
        return talents;
    }

    private List<EmployeeStatus> generateEmployeeStatusList() {
        List<EmployeeStatus> employeeStatuses = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("employee_statuses.json");
        if (resource == null) {
            return employeeStatuses;
        }
        try {
            File jsonFile = new File(resource.getFile());
            employeeStatuses = objectMapper.readValue(jsonFile, new TypeReference<List<EmployeeStatus>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeStatuses;
    }

    private List<TalentLevel> generateTalentLevelList() {
        List<TalentLevel> talentLevels = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("talent_levels.json");
        if (resource == null) {
            return talentLevels;
        }
        try {
            File jsonFile = new File(resource.getFile());
            talentLevels = objectMapper.readValue(jsonFile, new TypeReference<List<TalentLevel>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return talentLevels;
    }

    private List<TalentStatus> generateTalentStatusList() {
        List<TalentStatus> talentStatuses = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("talent_statuses.json");
        if (resource == null) {
            return talentStatuses;
        }
        try {
            File jsonFile = new File(resource.getFile());
            talentStatuses = objectMapper.readValue(jsonFile, new TypeReference<List<TalentStatus>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return talentStatuses;
    }

    private List<TalentRequestStatus> generateTalentRequestStatusList() {
        List<TalentRequestStatus> talentRequestStatuses = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("talent_request_statuses.json");
        if (resource == null) {
            return talentRequestStatuses;
        }
        try {
            File jsonFile = new File(resource.getFile());
            talentRequestStatuses = objectMapper.readValue(jsonFile, new TypeReference<List<TalentRequestStatus>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return talentRequestStatuses;
    }

    private List<Position> generatePositionList() {
        List<Position> positions = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("positions.json");
        if (resource == null) {
            return positions;
        }
        try {
            File jsonFile = new File(resource.getFile());
            positions = objectMapper.readValue(jsonFile, new TypeReference<List<Position>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return positions;
    }

    private List<Skillset> generateSkillsetList() {
        List<Skillset> skillsets = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("skillsets.json");
        if (resource == null) {
            return skillsets;
        }
        try {
            File jsonFile = new File(resource.getFile());
            skillsets = objectMapper.readValue(jsonFile, new TypeReference<List<Skillset>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return skillsets;
    }

    private List<Client> generateClientList() {
        List<Client> clients = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("clients.json");
        if (resource == null) {
            return clients;
        }
        try {
            File jsonFile = new File(resource.getFile());
            clients = objectMapper.readValue(jsonFile, new TypeReference<List<Client>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }
    
    private List<Talent> generateTalentList() {
        List<Talent> talents = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("talents.json");
        if (resource == null) {
            return talents;
        }
        try {
            File jsonFile = new File(resource.getFile());
            talents = objectMapper.readValue(jsonFile, new TypeReference<List<Talent>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return talents;
    }

    private List<TalentRequest> generateTalentRequestList() {
        List<TalentRequest> talentRequests = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("talent_request.json");
        if (resource == null) {
            return talentRequests;
        }
        try {
            File jsonFile = new File(resource.getFile());
            talentRequests = objectMapper.readValue(jsonFile, new TypeReference<List<TalentRequest>>(){});
            for (TalentRequest request : talentRequests) {
                UUID wishlistId = request.getTalentWishlist().getTalentWishlistId();
                UUID requestStatusId = request.getTalentRequestStatus().getTalentRequestStatusId();
                TalentRequestStatus requestStatus = talentRequestStatuses.stream().filter(item -> item.getTalentRequestStatusId().equals(requestStatusId)).findFirst().get();
                TalentWishlist wishlist = talentWishlists.stream().filter(item -> item.getTalentWishlistId().equals(wishlistId)).findFirst().get();
                request.setTalentWishlist(wishlist);
                request.setTalentRequestStatus(requestStatus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return talentRequests;
    }

    private List<TalentWishlist> generateTalentWishlistList() {
        List<TalentWishlist> wishlists = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("talent_wishlist.json");
        if (resource == null) {
            return wishlists;
        }
        try {
            File jsonFile = new File(resource.getFile());
            wishlists = objectMapper.readValue(jsonFile, new TypeReference<List<TalentWishlist>>(){});
            for (TalentWishlist wishlist : wishlists) {
                UUID talentId = wishlist.getTalent().getTalentId();
                Talent talent = talents.stream().filter(item -> item.getTalentId().equals(talentId)).findFirst().get();
                wishlist.setTalent(talent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wishlists;
    }
    

    private List<User> generateUserList() {
        List<User> users = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("users.json");
        if (resource == null) return users;
        try {
            File jsonFile = new File(resource.getFile());
            users = objectMapper.readValue(jsonFile, new TypeReference<List<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static DummyGenerator getInstance() {
        if (INSTANCE == null) {
            synchronized(DummyGenerator.class) {
                INSTANCE = new DummyGenerator();
            }
        }
        return INSTANCE;
    } 
}
