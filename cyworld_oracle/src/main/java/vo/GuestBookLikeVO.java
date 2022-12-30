package vo;

public class GuestBookLikeVO {
	private int guestbookLikeIdx, guestbookLikeRef, guestbookLikeSession; // 좋아요를 받을 해당 유저의 방명록 idx, 방명록 번호, 로그인한 사용자
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// getter / setter
	
	public int getGuestbookLikeIdx() {
		return guestbookLikeIdx;
	}

	public void setGuestbookLikeIdx(int guestbookLikeIdx) {
		this.guestbookLikeIdx = guestbookLikeIdx;
	}

	public int getGuestbookLikeRef() {
		return guestbookLikeRef;
	}

	public void setGuestbookLikeRef(int guestbookLikeRef) {
		this.guestbookLikeRef = guestbookLikeRef;
	}

	public int getGuestbookLikeSession() {
		return guestbookLikeSession;
	}

	public void setGuestbookLikeSession(int guestbookLikeSession) {
		this.guestbookLikeSession = guestbookLikeSession;
	}
}