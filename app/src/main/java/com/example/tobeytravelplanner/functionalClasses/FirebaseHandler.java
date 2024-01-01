package com.example.tobeytravelplanner.functionalClasses;

import androidx.annotation.Nullable;

import com.example.tobeytravelplanner.MainActivity;
import com.example.tobeytravelplanner.R;
import com.example.tobeytravelplanner.objects.Message;
import com.example.tobeytravelplanner.adapters.conversationsAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FirebaseHandler {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public void updateMessages(MainActivity mainActivity, String sessionId, ArrayList<Message> messages) {
        firestore.collection("users").document(FirebaseAuth.getInstance().getUid())
                .collection(sessionId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                            return;
                        if (value == null) {
                            return;
                        }
                        mainActivity.stopLoading();
                        for (DocumentChange ds : value.getDocumentChanges()) {
                            if (ds.getType() == DocumentChange.Type.MODIFIED) {
                                Boolean iscopmlete = (Boolean) ds.getDocument().get("complete");
                                if (iscopmlete != null && iscopmlete) {
                                    mainActivity.completedChat();
                                }
                            }
                            if (ds.getType() != DocumentChange.Type.ADDED) {
                                continue;
                            }
                            Boolean iscopmlete = (Boolean) ds.getDocument().get("complete");
                            if (iscopmlete != null && iscopmlete) {
                                mainActivity.completedChat();
                            }
                            Message curr = ds.getDocument().toObject(Message.class);
                            messages.add(curr);
                            mainActivity.adapter.notifyItemChanged(messages.size() - 1);
                            mainActivity.send.setEnabled(!curr.getAuthor().equals("user"));
                            if (!curr.getAuthor().equals("user"))
                                mainActivity.send.setColorFilter(mainActivity.getColor(R.color.blue));
                            else
                                mainActivity.send.setColorFilter(mainActivity.getColor(R.color.grey));
                            mainActivity.messages.scrollToPosition(messages.size() - 1);
                        }
                    }
                });
    }

    public void addSessionToList(String sessionId) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<String> sessions = (List<String>) documentSnapshot.get("sessions");
                        if (sessions == null) {
                            sessions = new ArrayList<>();
                            sessions.add(sessionId);
                            firestore.collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("sessions", sessions);
                        } else {
                            sessions.add(sessionId);
                            firestore.collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("sessions", sessions);
                        }
                    }
                });
    }

    public void hotelSessionExists(String sessionId, Runnable runnable) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection(sessionId)
                .document("1")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if ((Boolean)value.get("hotelsExist")){
                            runnable.run();
                        }
                    }
                });
    }

    public void putImage(String[] place, Runnable runnable, String[] placeholder){
        firestore.collection("config")
                .document("Images")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        HashMap<String, String> map = (HashMap<String, String>) documentSnapshot.get("places");
                        if (place[0]==null)
                            return;
                        if(map.containsKey(place[0].toLowerCase())){
                            placeholder[0] = map.get(place[0].toLowerCase());
                            runnable.run();
                        }
                    }
                });
    }

    public void getHotelsFromSession(String sessionId, Runnable runOnRes, String[] ans, ArrayList<String> descs, ArrayList<String>prices) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection(sessionId + " : hotels")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!ans[0].isEmpty()){
                            ans[0]+=",";
                        }
                        for (QueryDocumentSnapshot ds : queryDocumentSnapshots) {
                            ans[0] += (String) ds.get("itenaryId");
                            ans[0] += ",";
                            String messageContent = (String)ds.get("messageContent");
                            descs.add(messageContent.substring(messageContent.indexOf("\n")+1));
                            prices.add(messageContent.substring(messageContent.indexOf(":")+2, messageContent.indexOf("\n")));
                        }
                        ans[0] = ans[0].substring(0, ans[0].length() - 1);
                        runOnRes.run();
                    }
                });
    }

    public void updateConversations(conversationsAdapter adapter, ArrayList<String> ids, ArrayList<String> locs, ArrayList<String> departTimes) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<String> arr = (List<String>) value.get("sessions");
                        if (arr == null) {
                            return;
                        }
                        ids.clear();
                        locs.clear();
                        departTimes.clear();
                        final int[] i = {0};
                        for (String curr : arr){
                            firestore.collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection(curr)
                                    .document("1")
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String locName = (String) documentSnapshot.get("location");
                                            String departTime = (String) documentSnapshot.get("departTime");
                                            if ((Boolean)documentSnapshot.get("complete")!=null&&locName.isEmpty()&&((Boolean) documentSnapshot.get("complete"))){
                                                locName=null;
                                                departTime = null;
                                            }
                                            locs.add(locName);
                                            departTimes.add(departTime);
                                            ids.add(curr);
                                            i[0]++;
                                            adapter.sortAll();
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                    }
                });
    }
}
