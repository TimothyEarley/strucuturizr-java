package com.structurizr.dsl;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DslTests extends AbstractTests {

    @Test
    void test_test() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/test.dsl"));

        assertFalse(parser.getWorkspace().isEmpty());
    }

    @Test
    void test_utf8() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/utf8.dsl"));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();

        assertEquals(1, model.getPeople().size());
        Person user = model.getPersonWithName("你好 Usér \uD83D\uDE42");
        assertNotNull(user);
    }

    @Test
    void test_gettingstarted() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/getting-started.dsl"));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        assertEquals(1, model.getPeople().size());
        Person user = model.getPersonWithName("User");

        assertEquals(1, workspace.getModel().getSoftwareSystems().size());
        SoftwareSystem softwareSystem = model.getSoftwareSystemWithName("Software System");

        assertEquals(1, workspace.getModel().getRelationships().size());
        Relationship relationship = user.getRelationships().iterator().next();
        assertEquals("Uses", relationship.getDescription());
        assertSame(softwareSystem, relationship.getDestination());

        assertEquals(1, views.getViews().size());
        assertEquals(1, views.getSystemContextViews().size());
        SystemContextView view = views.getSystemContextViews().iterator().next();
        assertEquals("SystemContext-001", view.getKey());
        assertEquals(2, view.getElements().size());
        assertEquals(1, view.getRelationships().size());
    }

    @Test
    void test_aws() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/amazon-web-services.dsl"));

        Workspace workspace = parser.getWorkspace();

        assertEquals(13, workspace.getModel().getElements().size());
        assertEquals(0, workspace.getModel().getPeople().size());
        assertEquals(1, workspace.getModel().getSoftwareSystems().size());
        assertEquals(2, workspace.getModel().getSoftwareSystemWithName("Spring PetClinic").getContainers().size());
        assertEquals(1, workspace.getModel().getDeploymentNodes().size());
        assertEquals(6, workspace.getModel().getElements().stream().filter(e -> e instanceof DeploymentNode).count());
        assertEquals(2, workspace.getModel().getElements().stream().filter(e -> e instanceof ContainerInstance).count());
        assertEquals(2, workspace.getModel().getElements().stream().filter(e -> e instanceof InfrastructureNode).count());

        assertEquals(4, workspace.getModel().getRelationships().size());

        assertEquals(0, workspace.getViews().getSystemLandscapeViews().size());
        assertEquals(0, workspace.getViews().getSystemContextViews().size());
        assertEquals(0, workspace.getViews().getContainerViews().size());
        assertEquals(0, workspace.getViews().getComponentViews().size());
        assertEquals(0, workspace.getViews().getDynamicViews().size());
        assertEquals(1, workspace.getViews().getDeploymentViews().size());

        DeploymentView deploymentView = workspace.getViews().getDeploymentViews().iterator().next();
        assertEquals(10, deploymentView.getElements().size());
        assertEquals(3, deploymentView.getRelationships().size());
        assertEquals(4, deploymentView.getAnimations().size());

        assertEquals(3, workspace.getViews().getConfiguration().getStyles().getElements().size());
        assertEquals(0, workspace.getViews().getConfiguration().getStyles().getRelationships().size());

        assertEquals(1, workspace.getViews().getConfiguration().getThemes().length);
    }

    @Test
    void test_awsLocal() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/amazon-web-services-local.dsl"));

        Workspace workspace = parser.getWorkspace();

        assertEquals(13, workspace.getModel().getElements().size());
        assertEquals(0, workspace.getModel().getPeople().size());
        assertEquals(1, workspace.getModel().getSoftwareSystems().size());
        assertEquals(2, workspace.getModel().getSoftwareSystemWithName("Spring PetClinic").getContainers().size());
        assertEquals(1, workspace.getModel().getDeploymentNodes().size());
        assertEquals(6, workspace.getModel().getElements().stream().filter(e -> e instanceof DeploymentNode).count());
        assertEquals(2, workspace.getModel().getElements().stream().filter(e -> e instanceof ContainerInstance).count());
        assertEquals(2, workspace.getModel().getElements().stream().filter(e -> e instanceof InfrastructureNode).count());

        assertEquals(4, workspace.getModel().getRelationships().size());

        assertEquals(0, workspace.getViews().getSystemLandscapeViews().size());
        assertEquals(0, workspace.getViews().getSystemContextViews().size());
        assertEquals(0, workspace.getViews().getContainerViews().size());
        assertEquals(0, workspace.getViews().getComponentViews().size());
        assertEquals(0, workspace.getViews().getDynamicViews().size());
        assertEquals(1, workspace.getViews().getDeploymentViews().size());

        DeploymentView deploymentView = workspace.getViews().getDeploymentViews().iterator().next();
        assertEquals(10, deploymentView.getElements().size());
        assertEquals(3, deploymentView.getRelationships().size());
        assertEquals(4, deploymentView.getAnimations().size());

        assertEquals(3, workspace.getViews().getConfiguration().getStyles().getElements().size());
        assertEquals(0, workspace.getViews().getConfiguration().getStyles().getRelationships().size());

        assertEquals(1, workspace.getViews().getConfiguration().getThemes().length);
    }

    @Test
    void test_bigbankplc() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/big-bank-plc.dsl"));

        Workspace workspace = parser.getWorkspace();

        assertEquals(51, workspace.getModel().getElements().size());
        assertEquals(3, workspace.getModel().getPeople().size());
        assertEquals(4, workspace.getModel().getSoftwareSystems().size());
        assertEquals(5, workspace.getModel().getSoftwareSystemWithName("Internet Banking System").getContainers().size());
        assertEquals(6, workspace.getModel().getSoftwareSystemWithName("Internet Banking System").getContainerWithName("API Application").getComponents().size());
        assertEquals(5, workspace.getModel().getDeploymentNodes().size());
        assertEquals(21, workspace.getModel().getElements().stream().filter(e -> e instanceof DeploymentNode).count());
        assertEquals(2, workspace.getModel().getElements().stream().filter(e -> e instanceof SoftwareSystemInstance).count());
        assertEquals(10, workspace.getModel().getElements().stream().filter(e -> e instanceof ContainerInstance).count());
        assertEquals(0, workspace.getModel().getElements().stream().filter(e -> e instanceof InfrastructureNode).count());

        assertEquals(42, workspace.getModel().getRelationships().size());

        assertEquals(1, workspace.getViews().getSystemLandscapeViews().size());
        assertEquals(1, workspace.getViews().getSystemContextViews().size());
        assertEquals(1, workspace.getViews().getContainerViews().size());
        assertEquals(1, workspace.getViews().getComponentViews().size());
        assertEquals(1, workspace.getViews().getDynamicViews().size());
        assertEquals(2, workspace.getViews().getDeploymentViews().size());

        assertEquals(7, workspace.getViews().getSystemLandscapeViews().iterator().next().getElements().size());
        assertEquals(9, workspace.getViews().getSystemLandscapeViews().iterator().next().getRelationships().size());

        assertEquals(4, workspace.getViews().getSystemContextViews().iterator().next().getElements().size());
        assertEquals(4, workspace.getViews().getSystemContextViews().iterator().next().getRelationships().size());

        assertEquals(8, workspace.getViews().getContainerViews().iterator().next().getElements().size());
        assertEquals(10, workspace.getViews().getContainerViews().iterator().next().getRelationships().size());

        assertEquals(11, workspace.getViews().getComponentViews().iterator().next().getElements().size());
        assertEquals(13, workspace.getViews().getComponentViews().iterator().next().getRelationships().size());

        assertEquals(4, workspace.getViews().getDynamicViews().iterator().next().getElements().size());
        assertEquals(6, workspace.getViews().getDynamicViews().iterator().next().getRelationships().size());

        assertEquals(13, workspace.getViews().getDeploymentViews().stream().filter(v -> v.getKey().equals("DevelopmentDeployment")).findFirst().get().getElements().size());
        assertEquals(4, workspace.getViews().getDeploymentViews().stream().filter(v -> v.getKey().equals("DevelopmentDeployment")).findFirst().get().getRelationships().size());

        assertEquals(20, workspace.getViews().getDeploymentViews().stream().filter(v -> v.getKey().equals("LiveDeployment")).findFirst().get().getElements().size());
        assertEquals(7, workspace.getViews().getDeploymentViews().stream().filter(v -> v.getKey().equals("LiveDeployment")).findFirst().get().getRelationships().size());

        assertEquals(11, workspace.getViews().getConfiguration().getStyles().getElements().size());
        assertEquals(0, workspace.getViews().getConfiguration().getStyles().getRelationships().size());

        assertEquals(0, workspace.getViews().getConfiguration().getThemes().length);

        assertEquals(0, workspace.getDocumentation().getSections().size());
        assertEquals(0, workspace.getDocumentation().getDecisions().size());
    }

    @Test
    void test_bigbankplc_systemlandscape() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/big-bank-plc/system-landscape.dsl"));

        Workspace workspace = parser.getWorkspace();

        assertEquals(7, workspace.getModel().getElements().size());
        assertEquals(3, workspace.getModel().getPeople().size());
        assertEquals(4, workspace.getModel().getSoftwareSystems().size());

        assertEquals(9, workspace.getModel().getRelationships().size());

        assertEquals(1, workspace.getViews().getSystemLandscapeViews().size());
        assertEquals(0, workspace.getViews().getSystemContextViews().size());
        assertEquals(0, workspace.getViews().getContainerViews().size());
        assertEquals(0, workspace.getViews().getComponentViews().size());
        assertEquals(0, workspace.getViews().getDynamicViews().size());
        assertEquals(0, workspace.getViews().getDeploymentViews().size());

        assertEquals(7, workspace.getViews().getSystemLandscapeViews().iterator().next().getElements().size());
        assertEquals(9, workspace.getViews().getSystemLandscapeViews().iterator().next().getRelationships().size());

        assertEquals(4, workspace.getViews().getConfiguration().getStyles().getElements().size());
        assertEquals(0, workspace.getViews().getConfiguration().getStyles().getRelationships().size());

        assertEquals(0, workspace.getViews().getConfiguration().getThemes().length);
    }

    @Test
    void test_bigbankplc_internetbankingsystem() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/big-bank-plc/internet-banking-system.dsl"));

        Workspace workspace = parser.getWorkspace();

        assertEquals(51, workspace.getModel().getElements().size());
        assertEquals(3, workspace.getModel().getPeople().size());
        assertEquals(4, workspace.getModel().getSoftwareSystems().size());
        assertEquals(5, workspace.getModel().getSoftwareSystemWithName("Internet Banking System").getContainers().size());
        assertEquals(6, workspace.getModel().getSoftwareSystemWithName("Internet Banking System").getContainerWithName("API Application").getComponents().size());
        assertEquals(5, workspace.getModel().getDeploymentNodes().size());
        assertEquals(21, workspace.getModel().getElements().stream().filter(e -> e instanceof DeploymentNode).count());
        assertEquals(2, workspace.getModel().getElements().stream().filter(e -> e instanceof SoftwareSystemInstance).count());
        assertEquals(10, workspace.getModel().getElements().stream().filter(e -> e instanceof ContainerInstance).count());
        assertEquals(0, workspace.getModel().getElements().stream().filter(e -> e instanceof InfrastructureNode).count());

        assertEquals(42, workspace.getModel().getRelationships().size());

        assertEquals(0, workspace.getViews().getSystemLandscapeViews().size());
        assertEquals(1, workspace.getViews().getSystemContextViews().size());
        assertEquals(1, workspace.getViews().getContainerViews().size());
        assertEquals(1, workspace.getViews().getComponentViews().size());
        assertEquals(1, workspace.getViews().getDynamicViews().size());
        assertEquals(2, workspace.getViews().getDeploymentViews().size());

        assertEquals(4, workspace.getViews().getSystemContextViews().iterator().next().getElements().size());
        assertEquals(4, workspace.getViews().getSystemContextViews().iterator().next().getRelationships().size());

        assertEquals(8, workspace.getViews().getContainerViews().iterator().next().getElements().size());
        assertEquals(10, workspace.getViews().getContainerViews().iterator().next().getRelationships().size());

        assertEquals(11, workspace.getViews().getComponentViews().iterator().next().getElements().size());
        assertEquals(13, workspace.getViews().getComponentViews().iterator().next().getRelationships().size());

        assertEquals(4, workspace.getViews().getDynamicViews().iterator().next().getElements().size());
        assertEquals(6, workspace.getViews().getDynamicViews().iterator().next().getRelationships().size());

        assertEquals(13, workspace.getViews().getDeploymentViews().stream().filter(v -> v.getKey().equals("DevelopmentDeployment")).findFirst().get().getElements().size());
        assertEquals(4, workspace.getViews().getDeploymentViews().stream().filter(v -> v.getKey().equals("DevelopmentDeployment")).findFirst().get().getRelationships().size());

        assertEquals(20, workspace.getViews().getDeploymentViews().stream().filter(v -> v.getKey().equals("LiveDeployment")).findFirst().get().getElements().size());
        assertEquals(7, workspace.getViews().getDeploymentViews().stream().filter(v -> v.getKey().equals("LiveDeployment")).findFirst().get().getRelationships().size());

        assertEquals(11, workspace.getViews().getConfiguration().getStyles().getElements().size());
        assertEquals(0, workspace.getViews().getConfiguration().getStyles().getRelationships().size());

        assertEquals(0, workspace.getViews().getConfiguration().getThemes().length);

        assertEquals(4, workspace.getModel().getSoftwareSystemWithName("Internet Banking System").getDocumentation().getSections().size());
        assertEquals(1, workspace.getModel().getSoftwareSystemWithName("Internet Banking System").getDocumentation().getDecisions().size());
    }

    @Test
    void test_frs() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/financial-risk-system.dsl"));


        Workspace workspace = parser.getWorkspace();

        assertEquals(9, workspace.getModel().getElements().size());
        assertEquals(2, workspace.getModel().getPeople().size());
        assertEquals(7, workspace.getModel().getSoftwareSystems().size());
        assertEquals(0, workspace.getModel().getDeploymentNodes().size());
        assertEquals(0, workspace.getModel().getElements().stream().filter(e -> e instanceof DeploymentNode).count());
        assertEquals(0, workspace.getModel().getElements().stream().filter(e -> e instanceof ContainerInstance).count());
        assertEquals(0, workspace.getModel().getElements().stream().filter(e -> e instanceof InfrastructureNode).count());

        assertEquals(9, workspace.getModel().getRelationships().size());

        assertEquals(0, workspace.getViews().getSystemLandscapeViews().size());
        assertEquals(1, workspace.getViews().getSystemContextViews().size());
        assertEquals(0, workspace.getViews().getContainerViews().size());
        assertEquals(0, workspace.getViews().getComponentViews().size());
        assertEquals(0, workspace.getViews().getDynamicViews().size());
        assertEquals(0, workspace.getViews().getDeploymentViews().size());

        assertEquals(9, workspace.getViews().getSystemContextViews().iterator().next().getElements().size());
        assertEquals(9, workspace.getViews().getSystemContextViews().iterator().next().getRelationships().size());

        assertEquals(5, workspace.getViews().getConfiguration().getStyles().getElements().size());
        assertEquals(4, workspace.getViews().getConfiguration().getStyles().getRelationships().size());

        assertEquals(0, workspace.getViews().getConfiguration().getThemes().length);
    }

    @Test
    void test_includeLocalFile() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/include-file.dsl"));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();

        assertEquals(1, model.getSoftwareSystems().size());
        assertNotNull(model.getSoftwareSystemWithName("Software System"));

        assertEquals("workspace {\n" +
                "\n" +
                "    model {\n" +
                "        softwareSystem = softwareSystem \"Software System\" {\n" +
                "            !docs docs\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}\n", new String(Base64.getDecoder().decode(workspace.getProperties().get("structurizr.dsl"))));
    }

    @Test
    void test_includeLocalDirectory() throws Exception {
        File hiddenFile = new File("src/test/resources/dsl/include/model/software-system/.DS_Store");
        if (hiddenFile.exists()) {
            hiddenFile.delete();
        }

        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/include-directory.dsl"));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();

        assertEquals(3, workspace.getModel().getSoftwareSystems().size());
        SoftwareSystem softwareSystem1 = model.getSoftwareSystemWithName("Software System 1");
        assertNotNull(softwareSystem1);
        assertEquals(1, softwareSystem1.getDocumentation().getSections().size());

        SoftwareSystem softwareSystem2 = model.getSoftwareSystemWithName("Software System 2");
        assertNotNull(softwareSystem2);
        assertEquals(1, softwareSystem2.getDocumentation().getSections().size());

        SoftwareSystem softwareSystem3 = model.getSoftwareSystemWithName("Software System 3");
        assertNotNull(softwareSystem3);
        assertEquals(1, softwareSystem3.getDocumentation().getSections().size());

        assertEquals("workspace {\n" +
                "\n" +
                "    model {\n" +
                "        !constant SOFTWARE_SYSTEM_NAME \"Software System 1\"\n" +
                "        softwareSystem \"${SOFTWARE_SYSTEM_NAME}\" {\n" +
                "            !docs ../../docs\n" +
                "        }\n" +
                "\n" +
                "        !constant SOFTWARE_SYSTEM_NAME \"Software System 2\"\n" +
                "        softwareSystem \"${SOFTWARE_SYSTEM_NAME}\" {\n" +
                "            !docs ../../docs\n" +
                "        }\n" +
                "\n" +
                "        !constant SOFTWARE_SYSTEM_NAME \"Software System 3\"\n" +
                "        softwareSystem \"${SOFTWARE_SYSTEM_NAME}\" {\n" +
                "            !docs ../../docs\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}\n", new String(Base64.getDecoder().decode(workspace.getProperties().get("structurizr.dsl"))));
    }

    @Test
    void test_includeLocalDirectory_WhenThereAreHiddenFiles() throws Exception {
        File hiddenFile = new File("src/test/resources/dsl/include/model/software-system/.DS_Store");
        if (hiddenFile.exists()) {
            hiddenFile.delete();
        }
        Files.writeString(hiddenFile.toPath(), "hello world");

        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/include-directory.dsl"));
    }

    @Test
    void test_includeUrl() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/include-url.dsl"));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        assertEquals(1, workspace.getModel().getSoftwareSystems().size());
        SoftwareSystem softwareSystem = model.getSoftwareSystemWithName("Software System");

        assertEquals("workspace {\n" +
                "\n" +
                "    model {\n" +
                "        softwareSystem = softwareSystem \"Software System\" {\n" +
                "            !docs docs\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}\n", new String(Base64.getDecoder().decode(workspace.getProperties().get("structurizr.dsl"))));
    }

    @Test
    void test_include_WhenRunningInRestrictedMode() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.setRestricted(true);

        // the model include will be ignored, so no software systems
        parser.parse(new File("src/test/resources/dsl/include-file.dsl"));
        assertEquals(0, model.getSoftwareSystems().size());
    }

    @ParameterizedTest
    @ValueSource(strings = { "src/test/resources/dsl/extend/extend-workspace-from-json-file.dsl", "src/test/resources/dsl/extend/extend-workspace-from-json-url.dsl" })
    void test_extendWorkspaceFromJson(String dslFile) throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File(dslFile));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();
        assertEquals(CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy.class, model.getImpliedRelationshipsStrategy().getClass());

        assertEquals(1, model.getPeople().size());
        Person user = model.getPersonWithName("User");

        assertEquals(3, workspace.getModel().getSoftwareSystems().size());
        SoftwareSystem softwareSystem = model.getSoftwareSystemWithName("Software System 1");
        assertTrue(user.hasEfferentRelationshipWith(softwareSystem, "Uses"));

        assertEquals(2, softwareSystem.getContainers().size());
        assertNotNull(softwareSystem.getContainers().stream().filter(c -> c.getName().equals("Web Application 1")).findFirst());
        assertNotNull(softwareSystem.getContainers().stream().filter(c -> c.getName().equals("Web Application 2")).findFirst());
    }

    @Test
    void test_extendWorkspaceFromJsonFile_WhenRunningInRestrictedMode() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.setRestricted(true);

        File dslFile = new File("src/test/resources/dsl/extend/extend-workspace-from-json-file.dsl");

        try {
            // this will fail, because the model import will be ignored
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Cannot import workspace from a file when running in restricted mode at line 1 of " + dslFile.getAbsolutePath() + ": workspace extends workspace.json {", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "src/test/resources/dsl/extend/extend-workspace-from-dsl-file.dsl", "src/test/resources/dsl/extend/extend-workspace-from-dsl-url.dsl" })
    void test_extendWorkspaceFromDsl(String dslFile) throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File(dslFile));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();
        assertEquals(CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy.class, model.getImpliedRelationshipsStrategy().getClass());

        assertEquals(1, model.getPeople().size());
        Person user = model.getPersonWithName("User");

        assertEquals(3, workspace.getModel().getSoftwareSystems().size());
        SoftwareSystem softwareSystem = model.getSoftwareSystemWithName("Software System 1");
        assertTrue(user.hasEfferentRelationshipWith(softwareSystem, "Uses"));

        assertEquals(1, softwareSystem.getContainers().size());
        assertEquals("Web Application", softwareSystem.getContainers().iterator().next().getName());
    }

    @Test
    void test_extendWorkspaceFromDslFile_WhenRunningInRestrictedMode() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.setRestricted(true);

        File dslFile = new File("src/test/resources/dsl/extend/extend-workspace-from-dsl-file.dsl");
        try {
            // this will fail, because the model import will be ignored
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Cannot import workspace from a file when running in restricted mode at line 1 of " + dslFile.getAbsolutePath() +": workspace extends workspace.dsl {", e.getMessage());
        }
    }

    @Test
    void test_extendWorkspaceFromDslFiles() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/extend/4.dsl"));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        assertEquals(3, model.getPeople().size());
        assertEquals(1, views.getViews().size());
    }

    @Test
    void test_ref() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/ref.dsl"));

        assertNotNull(parser.getWorkspace().getModel().getElementWithCanonicalName("InfrastructureNode://Live/Amazon Web Services/New deployment node/New infrastructure node"));
        assertNotNull(parser.getWorkspace().getModel().getElementWithCanonicalName("InfrastructureNode://Live/Amazon Web Services/US-East-1/New deployment node 1/New infrastructure node 1"));
        assertNotNull(parser.getWorkspace().getModel().getElementWithCanonicalName("InfrastructureNode://Live/Amazon Web Services/US-East-1/New deployment node 2/New infrastructure node 2"));
    }

    @Test
    void test_parallel1() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/parallel1.dsl"));

        assertFalse(parser.getWorkspace().isEmpty());
        DynamicView view = parser.getWorkspace().getViews().getDynamicViews().iterator().next();
        List<RelationshipView> relationships = new ArrayList<>(view.getRelationships());
        assertEquals(4, relationships.size());
        assertEquals("1", relationships.get(0).getOrder());
        assertEquals("2", relationships.get(1).getOrder());
        assertEquals("3", relationships.get(2).getOrder());
        assertEquals("3", relationships.get(3).getOrder());
    }

    @Test
    void test_parallel2() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/parallel2.dsl"));

        assertFalse(parser.getWorkspace().isEmpty());
        DynamicView view = parser.getWorkspace().getViews().getDynamicViews().iterator().next();
        List<RelationshipView> relationships = new ArrayList<>(view.getRelationships());
        assertEquals(4, relationships.size());
        assertEquals("1", relationships.get(0).getOrder());
        assertEquals("2", relationships.get(1).getOrder());
        assertEquals("2", relationships.get(2).getOrder());
        assertEquals("3", relationships.get(3).getOrder());
    }

    @Test
    void test_groups() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/groups.dsl"));

        ContainerView containerView = parser.getWorkspace().getViews().getContainerViews().iterator().next();
        assertEquals(4, containerView.getElements().size());

        DeploymentView deploymentView = parser.getWorkspace().getViews().getDeploymentViews().iterator().next();
        assertEquals(6, deploymentView.getElements().size());
    }

    @Test
    void test_nested_groups() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/groups-nested.dsl"));

        SoftwareSystem a = parser.getWorkspace().getModel().getSoftwareSystemWithName("A");
        assertEquals("Organisation/Department A", a.getGroup());

        Container aApi = a.getContainerWithName("A API");
        assertEquals("Capability 1/Service A", aApi.getGroup());

        Container aDatabase = a.getContainerWithName("A Database");
        assertEquals("Capability 1/Service A", aDatabase.getGroup());

        Container bApi = a.getContainerWithName("B API");
        assertEquals("Capability 1/Service B", bApi.getGroup());

        Container bDatabase = a.getContainerWithName("B Database");
        assertEquals("Capability 1/Service B", bDatabase.getGroup());

        SoftwareSystem b = parser.getWorkspace().getModel().getSoftwareSystemWithName("B");
        assertEquals("Organisation/Department B", b.getGroup());

        SoftwareSystem c = parser.getWorkspace().getModel().getSoftwareSystemWithName("C");
        assertEquals("Organisation", c.getGroup());
    }

    @Test
    void test_hierarchicalIdentifiers() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/hierarchical-identifiers.dsl"));

        Workspace workspace = parser.getWorkspace();
        assertEquals(0, workspace.getModel().getSoftwareSystemWithName("B").getRelationships().size());
    }

    @Test
    void test_hierarchicalIdentifiersWhenUnassigned() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/hierarchical-identifiers-when-unassigned.dsl"));

        Workspace workspace = parser.getWorkspace();
        IdentifiersRegister identifiersRegister = parser.getIdentifiersRegister();

        assertEquals(6, identifiersRegister.getElementIdentifiers().size());
        for (String identifier : identifiersRegister.getElementIdentifiers()) {
            assertFalse(identifier.startsWith("null"));
        }
    }

    @Test
    void test_hierarchicalIdentifiersAndDeploymentNodes() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/hierarchical-identifiers-and-deployment-nodes-1.dsl"));
    }

    @Test
    void test_hierarchicalIdentifiersAndDeploymentNodes_WhenSoftwareSystemNameClashes() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/hierarchical-identifiers-and-deployment-nodes-2.dsl"));
    }

    @Test
    void test_hierarchicalIdentifiersAndDeploymentNodes_WhenSoftwareContainerClashes() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/hierarchical-identifiers-and-deployment-nodes-3.dsl"));
    }

    @Test
    void test_pluginWithoutParameters() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/plugin-without-parameters.dsl"));

        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Java"));
    }

    @Test
    void test_pluginWithParameters() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/plugin-with-parameters.dsl"));

        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Java"));
    }

    @Test
    void test_script() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/script-external.dsl"));

        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Groovy"));
        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Kotlin"));
        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Ruby"));
    }

    @Test
    void test_scriptWithParameters() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/script-external-with-parameters.dsl"));

        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Groovy"));
    }

    @Test
    void test_inlineScript() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/script-inline.dsl"));

        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Groovy"));
        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Kotlin"));
        assertNotNull(parser.getWorkspace().getModel().getPersonWithName("Ruby"));

        assertTrue(parser.getWorkspace().getModel().getPersonWithName("User").hasTag("Groovy"));
        assertTrue(parser.getWorkspace().getModel().getPersonWithName("User").getRelationships().iterator().next().hasTag("Groovy"));
        assertEquals("Groovy", parser.getWorkspace().getViews().getSystemLandscapeViews().iterator().next().getDescription());
    }

    @Test
    void test_docs() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/docs/workspace.dsl"));

        SoftwareSystem softwareSystem = parser.getWorkspace().getModel().getSoftwareSystemWithName("Software System");
        Container container = softwareSystem.getContainerWithName("Container");
        Component component = container.getComponentWithName("Component");

        assertEquals(1, parser.getWorkspace().getDocumentation().getSections().size());
        assertEquals(1, softwareSystem.getDocumentation().getSections().size());
        assertEquals(1, container.getDocumentation().getSections().size());
        assertEquals(1, component.getDocumentation().getSections().size());
    }

    @Test
    void test_decisions() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/decisions/workspace.dsl"));

        SoftwareSystem softwareSystem = parser.getWorkspace().getModel().getSoftwareSystemWithName("Software System");
        Container container = softwareSystem.getContainerWithName("Container");
        Component component = container.getComponentWithName("Component");

        // adrtools decisions
        assertEquals(10, parser.getWorkspace().getDocumentation().getDecisions().size());
        assertEquals(10, softwareSystem.getDocumentation().getDecisions().size());

        // madr decisions
        assertEquals(19, container.getDocumentation().getDecisions().size());

        // log4brains decisions
        assertEquals(4, component.getDocumentation().getDecisions().size());
    }

    @Test
    void test_this() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/this.dsl"));
    }

    @Test
    void test_workspaceWithControlCharacters() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/workspace-with-bom.dsl"));
    }

    @Test
    void test_excludeRelationships() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/exclude-relationships.dsl"));
    }

    @Test
    void test_unexpectedTokensBeforeWorkspace() {
        File dslFile = new File("src/test/resources/dsl/unexpected-tokens-before-workspace.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Unexpected tokens (expected: workspace) at line 1 of " + dslFile.getAbsolutePath() + ": hello world", e.getMessage());
        }
    }

    @Test
    void test_unexpectedTokensAfterWorkspace() {
        File dslFile = new File("src/test/resources/dsl/unexpected-tokens-after-workspace.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Unexpected tokens at line 4 of " + dslFile.getAbsolutePath() + ": hello world", e.getMessage());
        }
    }

    @Test
    void test_unexpectedTokensInWorkspace() {
        File dslFile = new File("src/test/resources/dsl/unexpected-tokens-in-workspace.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Unexpected tokens (expected: name, description, properties, !docs, !decisions, !identifiers, !impliedRelationships, model, views, configuration) at line 3 of " + dslFile.getAbsolutePath() + ": softwareSystem \"Name\"", e.getMessage());
        }
    }

    @Test
    void test_urlNotPermittedInGroup() {
        File dslFile = new File("src/test/resources/dsl/group-url.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Unexpected tokens (expected: !docs, !decisions, group, container, description, tags, url, properties, perspectives, ->) at line 6 of " + dslFile.getAbsolutePath() + ": url \"https://example.com\"", e.getMessage());
        }
    }

    @Test
    void test_multipleWorkspaceTokens_ThrowsAnException() {
        File dslFile = new File("src/test/resources/dsl/multiple-workspace-tokens.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Multiple workspaces are not permitted in a DSL definition at line 9 of " + dslFile.getAbsolutePath() + ": workspace {", e.getMessage());
        }
    }

    @Test
    void test_multipleModelTokens_ThrowsAnException() {
        File dslFile = new File("src/test/resources/dsl/multiple-model-tokens.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Multiple models are not permitted in a DSL definition at line 7 of " + dslFile.getAbsolutePath() + ": model {", e.getMessage());
        }
    }

    @Test
    void test_multipleViewTokens_ThrowsAnException() {
        File dslFile = new File("src/test/resources/dsl/multiple-view-tokens.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Multiple view sets are not permitted in a DSL definition at line 13 of " + dslFile.getAbsolutePath() + ": views {", e.getMessage());
        }
    }

    @Test
    void test_dynamicViewWithExplicitRelationships() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/dynamic-view-with-explicit-relationships.dsl"));
    }

    @Test
    void test_dynamicViewWithCustomElements() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/dynamic-view-with-custom-elements.dsl"));
    }

    @Test
    void test_workspaceProperties() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/workspace-properties.dsl"));

        assertEquals("false", parser.getWorkspace().getProperties().get("structurizr.dslEditor"));
    }

    @Test
    void test_viewsWithoutKeys() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/views-without-keys.dsl"));

        assertTrue(parser.getWorkspace().getViews().getSystemLandscapeViews().stream().anyMatch(view -> view.getKey().equals("SystemLandscape-001")));
        assertTrue(parser.getWorkspace().getViews().getSystemLandscapeViews().stream().anyMatch(view -> view.getKey().equals("SystemLandscape-002")));
        assertTrue(parser.getWorkspace().getViews().getSystemLandscapeViews().stream().anyMatch(view -> view.getKey().equals("SystemLandscape-003")));
    }

    @Test
    void test_identifiers() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/identifiers.dsl"));

        Workspace workspace = parser.getWorkspace();
        Model model = workspace.getModel();

        Person user = model.getPersonWithName("User");
        SoftwareSystem softwareSystem = model.getSoftwareSystemWithName("Software System");
        Container container = softwareSystem.getContainerWithName("Container");
        Relationship relationship = user.getEfferentRelationshipWith(container);
        Relationship impliedRelationship = user.getEfferentRelationshipWith(softwareSystem);

        IdentifiersRegister register = parser.getIdentifiersRegister();
        assertEquals("user", register.findIdentifier(user));
        assertEquals("softwaresystem", register.findIdentifier(softwareSystem));
        assertEquals("softwaresystem.container", register.findIdentifier(container));
        assertEquals("rel", register.findIdentifier(relationship));

        assertSame(user, register.getElement("user"));
        assertSame(softwareSystem, register.getElement("softwareSystem"));
        assertSame(container, register.getElement("softwareSystem.container"));
        assertSame(relationship, register.getRelationship("rel"));

        assertEquals("user", user.getProperties().get("structurizr.dsl.identifier"));
        assertEquals("softwaresystem", softwareSystem.getProperties().get("structurizr.dsl.identifier"));
        assertEquals("softwaresystem.container", container.getProperties().get("structurizr.dsl.identifier"));
        assertEquals("rel", relationship.getProperties().get("structurizr.dsl.identifier"));
        assertNull(impliedRelationship.getProperties().get("structurizr.dsl.identifier"));
    }

    @Test
    void test_imageViews_ViaFiles() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/image-views/workspace-via-file.dsl"));

        Workspace workspace = parser.getWorkspace();
        assertEquals(4, workspace.getViews().getImageViews().size());

        ImageView plantumlView = (ImageView)workspace.getViews().getViewWithKey("plantuml");
        assertEquals("diagram.puml", plantumlView.getTitle());
        assertEquals("http://localhost:7777/png/SoWkIImgAStDuNBAJrBGjLDmpCbCJbMmKiX8pSd9vt98pKi1IW80", plantumlView.getContent());
        assertEquals("image/png", plantumlView.getContentType());

        ImageView mermaidView = (ImageView)workspace.getViews().getViewWithKey("mermaid");
        assertEquals("diagram.mmd", mermaidView.getTitle());
        assertEquals("http://localhost:8888/img/Zmxvd2NoYXJ0IFRECiAgICBTdGFydCAtLT4gU3RvcA==?type=png", mermaidView.getContent());
        assertEquals("image/png", mermaidView.getContentType());

        ImageView krokiView = (ImageView)workspace.getViews().getViewWithKey("kroki");
        assertEquals("diagram.dot", krokiView.getTitle());
        assertEquals("http://localhost:9999/graphviz/png/eNpLyUwvSizIUHBXqPZIzcnJ17ULzy_KSanlAgB1EAjQ", krokiView.getContent());
        assertEquals("image/png", krokiView.getContentType());

        ImageView imageView = (ImageView)workspace.getViews().getViewWithKey("image");
        assertEquals("logo.png", imageView.getTitle());
        assertEquals("data:image/png;base64,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", imageView.getContent());
        assertEquals("image/png", imageView.getContentType());
    }

    @Test
    void test_imageViews_ViaUrls() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/image-views/workspace-via-url.dsl"));

        Workspace workspace = parser.getWorkspace();
        assertEquals(4, workspace.getViews().getImageViews().size());

        ImageView plantumlView = (ImageView)workspace.getViews().getViewWithKey("plantuml");
        assertEquals("diagram.puml", plantumlView.getTitle());
        assertEquals("http://localhost:7777/svg/SoWkIImgAStDuNBAJrBGjLDmpCbCJbMmKiX8pSd9vt98pKi1IW80", plantumlView.getContent());
        assertEquals("image/svg+xml", plantumlView.getContentType());

        ImageView mermaidView = (ImageView)workspace.getViews().getViewWithKey("mermaid");
        assertEquals("diagram.mmd", mermaidView.getTitle());
        assertEquals("http://localhost:8888/svg/Zmxvd2NoYXJ0IFRECiAgICBTdGFydCAtLT4gU3RvcA==", mermaidView.getContent());
        assertEquals("image/svg+xml", mermaidView.getContentType());

        ImageView krokiView = (ImageView)workspace.getViews().getViewWithKey("kroki");
        assertEquals("diagram.dot", krokiView.getTitle());
        assertEquals("http://localhost:9999/graphviz/svg/eNpLyUwvSizIUHBXqPZIzcnJ17ULzy_KSanlAgB1EAjQ", krokiView.getContent());
        assertEquals("image/svg+xml", krokiView.getContentType());

        ImageView imageView = (ImageView)workspace.getViews().getViewWithKey("image");
        assertEquals("logo.png", imageView.getTitle());
        assertEquals("https://raw.githubusercontent.com/structurizr/dsl/master/src/test/dsl/image-views/logo.png", imageView.getContent());
        assertEquals("image/png", imageView.getContentType());
    }

    @Test
    void test_EmptyDeploymentEnvironment() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/deployment-environment-empty.dsl"));

        assertEquals(1, parser.getWorkspace().getModel().getDeploymentNodes().size());
    }

    @Test
    void test_MultiLineSupport() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/multi-line.dsl"));

        assertNotNull(parser.getWorkspace().getModel().getSoftwareSystemWithName("Software System"));
    }

    @Test
    void test_MultiLineWithError() {
        File dslFile = new File("src/test/resources/dsl/multi-line-with-error.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            // check that the error message includes the original line number
            assertEquals("Unexpected tokens (expected: !docs, !decisions, group, container, description, tags, url, properties, perspectives, ->) at line 8 of " + dslFile.getAbsolutePath() + ": component \"Component\" // components not permitted inside software systems", e.getMessage());
        }
    }

    @Test
    void test_RelationshipAlreadyExists() {
        File dslFile = new File("src/test/resources/dsl/relationship-already-exists.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("A relationship between \"SoftwareSystem://B\" and \"SoftwareSystem://A\" already exists at line 10 of " + dslFile.getAbsolutePath() + ": b -> a", e.getMessage());
        }
    }

    @Test
    void test_ExcludeImpliedRelationship() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/exclude-implied-relationship.dsl"));

        // check the system landscape view doesn't include any relationships
        assertEquals(0, parser.getWorkspace().getViews().getSystemLandscapeViews().iterator().next().getRelationships().size());
    }

    @Test
    void test_IncludeImpliedRelationship() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(new File("src/test/resources/dsl/include-implied-relationship.dsl"));

        // check the system landscape view includes a relationship
        assertEquals(1, parser.getWorkspace().getViews().getSystemLandscapeViews().iterator().next().getRelationships().size());
    }

    @Test
    void test_GroupWithoutBrace() throws Exception {
        File dslFile = new File("src/test/resources/dsl/group-without-brace.dsl");

        try {
            StructurizrDslParser parser = new StructurizrDslParser();
            parser.parse(dslFile);
            fail();
        } catch (StructurizrDslParserException e) {
            assertEquals("Expected: group <name> { at line 4 of " + dslFile.getAbsolutePath() + ": group \"Name\"", e.getMessage());
        }
    }

    @Test
    void test_ISO8859Encoding() throws Exception {
        StructurizrDslParser parser = new StructurizrDslParser();
        parser.setCharacterEncoding(StandardCharsets.ISO_8859_1);
        parser.parse(new File("src/test/resources/dsl/iso-8859.dsl"));
        assertNotNull(parser.getWorkspace().getModel().getSoftwareSystemWithName("Namé"));
    }

    @Test
    void test_ScriptInDynamicView() throws Exception {
        File dslFile = new File("src/test/resources/dsl/script-in-dynamic-view.dsl");

        StructurizrDslParser parser = new StructurizrDslParser();
        parser.parse(dslFile);
    }

}