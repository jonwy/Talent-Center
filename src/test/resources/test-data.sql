-- INSERT For position
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Web Developer', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Web Developer Front End', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Web Developer Back End', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Android Developer', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Ios Developer', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Desktop Developer', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'UX/UI Designer', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Scrum Master', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Project Manager', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Analyst', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Video Game Developer', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Graphic Programmer', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Quality Assurance', true);
INSERT INTO public.position (position_id, position_name, is_active) 
            VALUES (RANDOM_UUID(), 'Data Analyst', true);

-- INSERT For skillset_type
INSERT INTO public.skillset_type (skillset_type_id, skillset_type_name, is_programming_skill, is_active) 
        VALUES (RANDOM_UUID(), 'Programming Language', true, true);
INSERT INTO public.skillset_type (skillset_type_id, skillset_type_name, is_programming_skill, is_active) 
        VALUES (RANDOM_UUID(), 'Framework', true, true);
INSERT INTO public.skillset_type (skillset_type_id, skillset_type_name, is_programming_skill, is_active) 
        VALUES (RANDOM_UUID(), 'Development Tools', true, true);
INSERT INTO public.skillset_type (skillset_type_id, skillset_type_name, is_programming_skill, is_active) 
        VALUES (RANDOM_UUID(), 'Hardware Skill', true, true);
INSERT INTO public.skillset_type (skillset_type_id, skillset_type_name, is_programming_skill, is_active) 
        VALUES (RANDOM_UUID(), 'Tech Support', true, true);
INSERT INTO public.skillset_type (skillset_type_id, skillset_type_name, is_programming_skill, is_active) 
        VALUES (RANDOM_UUID(), 'UI/UX', true, true);
INSERT INTO public.skillset_type (skillset_type_id, skillset_type_name, is_programming_skill, is_active) 
        VALUES (RANDOM_UUID(), 'Documentation', true, true);
INSERT INTO public.skillset_type (skillset_type_id, skillset_type_name, is_programming_skill, is_active) 
        VALUES (RANDOM_UUID(), 'Dev Ops', true, true);
        
-- insert for skillset
-- Programming Language
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'Java', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'C++', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'C', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'Kotlin', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'JavaScript', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'Golang', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'Php', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'Dart', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'Swift', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'Python', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Programming Language'), 'Ruby', true);

-- Framework
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Spring Boot', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'React JS', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Angular', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Svelte', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Next JS', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Django', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Remix', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Laravel', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Code Igniter', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Vue JS', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Framework'), 'Flutter', true);

-- UI/UX
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'UI/UX'), 'Figma', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'UI/UX'), 'Adobe Illustrator', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'UI/UX'), 'Adobe XD', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'UI/UX'), 'Skecth', true);

-- Development Tools
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Development Tools'), 'Visual Studio Code', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Development Tools'), 'Intellij Idea', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Development Tools'), 'Eclipse', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Development Tools'), 'NetBeans', true);

-- Dev Ops
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Dev Ops'), 'Git', true);                
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Dev Ops'), 'Docker', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active) 
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Dev Ops'), 'Kubernetes', true);
INSERT INTO public.skillset (skillset_id, skillset_type_id, skillset_name, is_active)
    VALUES (RANDOM_UUID(), 
            (SELECT st.skillset_type_id from skillset_type st 
                where st.skillset_type_name = 'Dev Ops'), 'Jenkins', true);


-- Insert for Talent Status
INSERT INTO talent_status (talent_status_id, talent_status_name, is_active) VALUES (RANDOM_UUID(), 'Onsite', true);
INSERT INTO talent_status (talent_status_id, talent_status_name, is_active) VALUES (RANDOM_UUID(), 'Not Onsite', true);

-- INSERT For Talent Level
INSERT INTO talent_level (talent_level_id, talent_level_name, is_active) VALUES (RANDOM_UUID(), 'Junior', true);
INSERT INTO talent_level (talent_level_id, talent_level_name, is_active) VALUES (RANDOM_UUID(), 'Middle', true);
INSERT INTO talent_level (talent_level_id, talent_level_name, is_active) VALUES (RANDOM_UUID(), 'Senior', true);

-- INSERT For employee_status
INSERT INTO employee_status (employee_status_id, employee_status_name) VALUES (RANDOM_UUID(), 'Active');
INSERT INTO employee_status (employee_status_id, employee_status_name) VALUES (RANDOM_UUID(), 'Not Active');

-- INSERT For talent_request_status
INSERT INTO talent_request_status(talent_request_status_id, talent_request_status_name, is_active) VALUES (RANDOM_UUID(), 'Approved', true);
INSERT INTO talent_request_status(talent_request_status_id, talent_request_status_name, is_active) VALUES (RANDOM_UUID(), 'Rejected', true);
INSERT INTO talent_request_status(talent_request_status_id, talent_request_status_name, is_active) VALUES (RANDOM_UUID(), 'On Progress', true);

-- INSERT for client_position
INSERT INTO client_position (client_position_id, client_position_name, is_active, created_by) VALUES (RANDOM_UUID(), 'Manager', true, 'admin');
INSERT INTO client_position (client_position_id, client_position_name, is_active, created_by) VALUES (RANDOM_UUID(), 'HRD', true, 'admin');
INSERT INTO client_position (client_position_id, client_position_name, is_active, created_by) VALUES (RANDOM_UUID(), 'Staff', true, 'admin');
