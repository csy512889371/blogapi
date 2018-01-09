package com.cto.edu.blog.core.user.service;

import com.cto.edu.blog.entity.user.UmsPerson;
import com.cto.edu.blog.facade.user.exception.UserUsernameExistsException;
import com.cto.edu.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("umsPersonService")
public class UmsPersonService extends BaseService<UmsPerson, String> {

    @Autowired
    private UmsUserService umsUserService;

    /**
     * 保存人员
     * @param person
     */
    public UmsPerson savePerson(UmsPerson person) {
        if(umsUserService.findByUsername(person.getUser().getUsername())!=null){
            throw new UserUsernameExistsException();
        }
        person = save(person);
//        UmsUserOrgGroupPos umsPersonOrgPos = new UmsUserOrgGroupPos();
//        umsPersonOrgPos.setUserId(person.getUser().getId());
//        umsPersonOrgPos.setOrgId(person.getOrgId());
//        umsPersonOrgPos.setPosId(person.getPosId());
//        umsUserOrgGroupPosRepository.save(umsPersonOrgPos);
        return person;
    }

    /**
     * 更新人员
     * @param person
     */
    public UmsPerson updatePerson(UmsPerson person){
        person = update(person);
//    	if(person.getUser().getDeleted()==BooleanEnum.FALSE.getValue()){
//    		UmsUserOrgGroupPos umsPersonOrgPos = umsUserOrgGroupPosRepository.findUmsUserOrgGroupPosByPersonId(person.getUser().getId());
//            umsPersonOrgPos.setOrgId(person.getOrgId());
//            umsPersonOrgPos.setPosId(person.getPosId());
//            umsUserOrgGroupPosRepository.save(umsPersonOrgPos);
//    	}
        return person;
    }
}