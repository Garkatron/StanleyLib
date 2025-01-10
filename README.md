# RPG System Documentation

## Skills System

The **Skills System** is managed by an instance of `SkillTree.java`. Below is a guide to understanding and using the system effectively.

---

### **Creating and Managing Skills**

Skills can be implemented by extending `FunctionalSkill.java` or implementing the `IFunctionalSkill` interface. These classes provide templates for creating custom skills.

For example, if you want to create a skill like **Super Strength**, you can extend `FunctionalSkill` and write the logic to enhance the player's strength.

#### **Timed Skills**
You can use templates like `TimedFunctionalSkill` if your skill requires pre-written cooldown logic.

---

### **Skill Implementation Example**

Here is a simple implementation of a custom skill:

```java
public class MySkill extends FunctionalSkill {
    public MySkill() {
        // Initialization logic (if needed)
    }
    
    @Override
    public boolean activate() {
        // Logic to activate the skill
        return false;
    }

    @Override
    public boolean deactivate() {
        // Logic to deactivate the skill
        return false;
    }

    @Override
    public boolean canActivate() {
        // Conditions for activation
        return false;
    }

    @Override
    public boolean canDeactivate() {
        // Conditions for deactivation
        return false;
    }

    /*
     * Optional methods:
     * - boolean canComplete();
     * - boolean complete();
     * - boolean cancel();
     */
}
```
### Creating Skills and Adding Them to a Skill Tree
Here is an example of how to create instances of Skill and manage them using a SkillTree:

```java
// Define skills with dependencies
Skill skill1 = new Skill("Super Strength", 0, "Increases player strength", false, 0, 0, 0, new int[]{}, new MySkill());
Skill skill2 = new Skill("Advanced Strength", 1, "Further increases player strength", false, 0, 0, 0, new int[]{0}, new MySkill());

// Create a SkillTree instance
SkillTree skillTree = new SkillTree(
    skill1,
    skill2
    // Add more skills as needed
);

// Unlocking skills
skillTree.unlockSkill(1); // Fails because Skill 1 depends on Skill 0
skillTree.unlockSkill(0); // Unlocks Skill 0
skillTree.unlockSkill(1); // Succeeds because Skill 0 is now unlocked

// Using skills
skillTree.getSkillById(0).getFunctionalSkill().activate();
skillTree.getSkillById(0).getFunctionalSkill().deactivate();

```
#### Key Concepts
Skill Dependencies: Skills can have dependencies. In the example above, Skill 1 depends on Skill 0 being unlocked first.
Functional Skills: These encapsulate the logic for what happens when a skill is activated, deactivated, completed, etc.
Skill Tree: A container for managing and unlocking skills, enforcing dependency rules.

#### Use FunctionalSkill for general-purpose skills and extend it for custom behavior.
Leverage TimedFunctionalSkill if your skill needs cooldown or duration logic.
Ensure skill dependencies are correctly defined using the integer array parameter in the Skill constructor.
