import java.util.ArrayList;
import java.util.Objects;

public class Member {
    private static int memberCount = 0;
    private static final ArrayList<ArrayList<String>> memberList = new ArrayList<>();

    public Member(String bookType) {
        memberCount++;
        String memberId = Integer.toString(memberCount);
        String memberTypeStr;
        if (Objects.equals(bookType, "S")){
            memberTypeStr = "Student";
        } else {
            memberTypeStr = "Academic";
        }
        ArrayList<String> member = new ArrayList<>();
        member.add(memberTypeStr);
        member.add(memberId);
        memberList.add(member);
        Main.write_output("Created new book: " + memberTypeStr + " [id: " + memberId + "]\n");
    }

    public static ArrayList<ArrayList<String>> getMemberList() {
        return memberList;
    }
}
