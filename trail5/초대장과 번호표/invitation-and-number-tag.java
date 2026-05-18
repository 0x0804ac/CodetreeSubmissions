import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

class Person {
    private int n; //n번 사람
    private boolean i; //초대장을 받았는지 여부
    private LinkedList<Group> l; //속한 그룹 리스트
    
    public Person(int id) {
        n = id;
        i = false;
        l = new LinkedList<Group>();
    }

    public int getId() { return n; }
    public boolean isInvited() { return i; }
    public LinkedList<Group> getGroups() { return l; }

    public void setInvited(boolean value) { i = value; }

    public void addGroup(Group group) { l.add(group); }
    public void removeGroup(Group group) {
        l.remove(group);
        group.removeMember(this);
    }
}

class Group {
    private int len;
    private HashSet<Person> set;

    public Group(int size) {
        len = size;
        set = new HashSet<Person>(len * 4 / 3 + 2);
    }

    public HashSet<Person> getMembers() { return set; }

    public void addMember(Person member) {
        if(set.contains(member)) return;
        if(set.size() < len) set.add(member);
    }
    public void removeMember(Person member) { set.remove(member); }
    public int getMemberCount() { return set.size(); }
}

public class Main {
    public static void invite(Person person) {
        if(person.isInvited()) return;
        person.setInvited(true);
        for(Group g : person.getGroups()) g.removeMember(person);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //사람 수(1 ~ 100000)
        int g = sc.nextInt(); //그룹 수(2 ~ 250000)
        LinkedList<Person> queue = new LinkedList<>();
        Person[] people = new Person[n + 1];
        Group[] groups = new Group[g];
        int i, j;
        for(i=1; i<=n; i++) people[i] = new Person(i);
        for(i=0; i<g; i++) {
            int count = sc.nextInt();
            groups[i] = new Group(count);
            for(j=0; j<count; j++) {
                int number = sc.nextInt();
                groups[i].addMember(people[number]);
                people[number].addGroup(groups[i]);
            }
        }
        int output = 1;
        invite(people[1]);
        queue.add(people[1]);
        while(!queue.isEmpty()) {
            Person next = queue.poll();
            for(Group group : next.getGroups()) {
                if(group.getMemberCount() == 1) {
                    for(Person p : group.getMembers()) {
                        invite(p);
                        queue.add(p);
                        output++;
                    }
                }
            }
        }
        System.out.println(output);
    }
}